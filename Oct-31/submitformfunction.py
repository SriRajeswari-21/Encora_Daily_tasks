import json
import boto3
import uuid
from datetime import datetime

dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table('UserSubmissions')

def lambda_handler(event, context):
    try:
        body = json.loads(event['body'])
        name = body['name']
        email = body['email']
        message = body['message']
        
        if not name or not email or not message:
            return {"statusCode": 400, "body": json.dumps({"error": "All fields are required"})}
        
        submission_id = str(uuid.uuid4())
        submission_date = datetime.utcnow().isoformat()
        
        table.put_item(
            Item={
                'submissionId': submission_id,
                'name': name,
                'email': email,
                'message': message,
                'submissionDate': submission_date,
                'status': 'submitted'
            }
        )
        
        return {
            "statusCode": 200,
            "headers": {"Access-Control-Allow-Origin": "*"},
            "body": json.dumps({"message": "Submission saved successfully", "submissionId": submission_id})
        }
    except Exception as e:
        return {"statusCode": 500, "body": json.dumps({"error": str(e)})}

