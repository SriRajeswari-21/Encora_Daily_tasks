package org.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class TextFileProcessor {

    // clients are thread-safe and can be reused
    private final S3Client s3 = S3Client.create();
    private final DynamoDbClient dynamo = DynamoDbClient.create();

    // Lambda handler
    public void handleRequest(S3Event s3event, Context context) {
        try {
            // assume single record (typical for simple test)
            var record = s3event.getRecords().get(0);
            String bucket = record.getS3().getBucket().getName();
            String key = record.getS3().getObject().getUrlDecodedKey();

            context.getLogger().log("Processing S3 object: " + bucket + "/" + key);

            // fetch object from S3
            GetObjectRequest getReq = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            try (ResponseInputStream<GetObjectResponse> s3Object = s3.getObject(getReq);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object, StandardCharsets.UTF_8))
            ) {
                int lineCount = 0;
                int wordCount = 0;
                int charCount = 0;
                StringBuilder allText = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    lineCount++;
                    // avoid counting empty string as a word
                    if (!line.isBlank()) {
                        String[] words = line.trim().split("\\s+");
                        wordCount += words.length;
                    }
                    charCount += line.length(); // characters in the line (no newline)
                    if (allText.length() > 0) allText.append(" ");
                    allText.append(line);
                }

                String text = allText.toString();
                String preview = text.length() > 100 ? text.substring(0, 100) : text;
                String processedDate = Instant.now().toString();

                // prepare item for DynamoDB
                Map<String, AttributeValue> item = new HashMap<>();
                item.put("fileName", AttributeValue.builder().s(key).build());
                item.put("lineCount", AttributeValue.builder().n(Integer.toString(lineCount)).build());
                item.put("wordCount", AttributeValue.builder().n(Integer.toString(wordCount)).build());
                item.put("charCount", AttributeValue.builder().n(Integer.toString(charCount)).build());
                item.put("preview", AttributeValue.builder().s(preview).build());
                item.put("processedDate", AttributeValue.builder().s(processedDate).build());

                // write to DynamoDB
                PutItemRequest putReq = PutItemRequest.builder()
                        .tableName("FileProcessingResults")
                        .item(item)
                        .build();

                dynamo.putItem(putReq);

                context.getLogger().log("Successfully processed and stored results for: " + key);
            }

        } catch (Exception e) {
            context.getLogger().log("Error processing S3 event: " + e.getMessage());
            // rethrow if you want Lambda to mark this as a failed execution
            throw new RuntimeException(e);
        }
    }
}
