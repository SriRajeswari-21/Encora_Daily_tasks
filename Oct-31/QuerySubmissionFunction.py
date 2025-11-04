      import json
import boto3

dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table('UserSubmissions')

def lambda_handler(event, context):
    try:
        email = event.get('queryStringParameters', {}).get('email') if event.get('queryStringParameters') else None
        
        if email:
            response = table.scan(
                FilterExpression="email = :e",
                ExpressionAttributeValues={":e": email}
            )
        else:
            response = table.scan()
        
        return {
            "statusCode": 200,
            "headers": {"Access-Control-Allow-Origin": "*"},
            "body": json.dumps(response['Items'])
        }
    except Exception as e:
        return {"statusCode": 500, "body": json.dumps({"error": str(e)})}
