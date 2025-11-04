# E-Commerce Order Processing Pipeline (Serverless)

## Overview

This project implements a **real-time data processing pipeline** for an e-commerce application using AWS services. It processes order events from DynamoDB and routes them to Lambda functions using EventBridge Pipes with filtering and error handling.

---

## Architecture

+----------------+
| Orders Table |
| (DynamoDB) |
+----------------+
|
v
+-------------------+
| DynamoDB Stream |
+-------------------+
|
v
+---------------------------+
| EventBridge Pipe #1 | ---> Lambda: General Orders
| Filter: pending/shipped |
| amount > 100 |
| customerEmail != test.com |
+---------------------------+
|
v
+---------------------------+
| EventBridge Pipe #2 | ---> Lambda: Premium Orders
| Filter: MODIFY |
| status: pending->shipped |
| amount > 1000 |
+---------------------------+
|
v
DLQs (SQS) for errors

yaml
Copy code

---

## AWS Services Used

- **DynamoDB**: Stores order data and streams CRUD events  
- **DynamoDB Streams**: Captures `NEW_AND_OLD_IMAGES`  
- **EventBridge Pipes**: Filters and routes events to Lambda  
- **Lambda Functions**: Process orders (normal and premium)  
- **IAM Roles**: Grant cross-service permissions  
- **SQS DLQs**: Handle failed events  

---

## Prerequisites

- AWS account with access to DynamoDB, Lambda, EventBridge, IAM, and SQS  
- AWS CLI or AWS Console access  

---

## Deployment Steps

1. **Create DynamoDB Table**
   - Table name: `Orders`
   - Partition Key: `orderId` (String)
   - Enable **Streams** → `NEW_AND_OLD_IMAGES`
2. **Add Sample Items**
   ```json
   {
     "orderId": "O1",
     "status": "pending",
     "amount": 150,
     "customerEmail": "abc@gmail.com"
   }
Create IAM Roles

Lambda execution role (LambdaOrdersRole) → DynamoDB read + CloudWatch logs

EventBridge Pipe role → DynamoDB read + Lambda invoke

DLQ role → SQS full access

Create Lambda Functions

ProcessNormalOrders → handles normal orders

ProcessPremiumOrders → handles high-value orders

Create EventBridge Pipes

Pipe #1: Normal orders → Filter status=pending|shipped, amount>100, customerEmail != test.com

Pipe #2: Premium orders → Filter MODIFY events where status changes pending→shipped and amount>1000

Set DLQs and retry logic

Test Pipeline

Insert or modify DynamoDB items

Check Lambda logs in CloudWatch

Verify DLQs for failed events

Lambda Function Example (Python)
python
Copy code
def lambda_handler(event, context):
    for record in event['Records']:
        order = record['dynamodb']['NewImage']
        print(f"Processing order: {order}")
    return {"statusCode": 200}
