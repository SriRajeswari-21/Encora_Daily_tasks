# Text File Processing System (AWS Lambda + S3 + DynamoDB)

## Project Overview
This project implements an automated system that processes text files uploaded to an S3 bucket using an AWS Lambda function written in Java (Java 17). The Lambda function reads the file, counts lines, words, characters, extracts a preview, and stores the results in a DynamoDB table.

---

## Architecture

1. **S3 Bucket**  
   - Bucket name: `file-processing-bucket-<your-name>`  
   - Uploading a `.txt` file triggers the Lambda function automatically.

2. **Lambda Function**  
   - Name: `TextFileProcessor`  
   - Runtime: Java 17  
   - Trigger: S3 ObjectCreated Event (for `.txt` files)  
   - Functionality:
     - Read uploaded text file from S3
     - Count lines, words, characters
     - Extract first 100 characters as preview
     - Save results in DynamoDB

3. **DynamoDB Table**  
   - Table name: `FileProcessingResults`  
   - Partition key: `fileName` (String)  
   - Attributes: `lineCount`, `wordCount`, `charCount`, `preview`, `processedDate`

---

## Getting Started

### Prerequisites
- AWS account with S3, Lambda, and DynamoDB access
- Java 17
- Maven
- Spring Tool Suite (STS) or any Java IDE

### Steps

1. **Clone the repository**
```bash
git clone <your-repo-url>
cd <project-folder>
Build the project

bash
Copy code
mvn clean package
This generates target/text-file-processor-1.0.0.jar

Upload JAR to AWS Lambda

Function name: TextFileProcessor

Runtime: Java 17

Handler: org.example.TextFileProcessor::handleRequest

Configure S3 Event Trigger

S3 bucket: file-processing-bucket-<your-name>

Event type: PUT

Suffix: .txt

Destination: Lambda → TextFileProcessor

Set IAM Permissions

Lambda execution role must have:

AmazonS3ReadOnlyAccess

AmazonDynamoDBFullAccess (or a policy allowing PutItem on FileProcessingResults table)

Test

Upload a .txt file to S3

Check DynamoDB table → FileProcessingResults

Confirm line count, word count, char count, preview, and processed date are stored correctly

Check CloudWatch logs for Lambda execution

Example
Input file (sample.txt):

scss
Copy code
Sample text file content
with multiple lines
for testing the processor
Expected DynamoDB item:

json
Copy code
{
  "fileName": "sample.txt",
  "lineCount": 3,
  "wordCount": 12,
  "charCount": 58,
  "preview": "Sample text file content with multiple lines for testing...",
  "processedDate": "2025-11-03T10:30:00Z"
}
