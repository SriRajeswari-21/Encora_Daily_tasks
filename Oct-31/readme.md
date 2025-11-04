# Serverless Web Application with AWS

This repository contains a **serverless web application** built on AWS, integrating **EC2**, **Lambda**, **API Gateway**, and **DynamoDB**. Users can submit a form, and the data is stored in DynamoDB. The application demonstrates a simple **frontend → API → backend → database** workflow.

---

## 🏗 Architecture Overview

[User Browser]
|
v
[EC2 - HTML Form + JS]
|
v (POST /submit, GET /submissions)
[API Gateway]
|
v
[Lambda Functions]
|
v
[DynamoDB Table]

markdown
Copy code

### Components

- **EC2**: Hosts the HTML form and JavaScript frontend.  
- **API Gateway**: Routes HTTP requests to Lambda functions.  
- **Lambda Functions**:  
  - `SubmitFormFunction` → Inserts data into DynamoDB  
  - `QuerySubmissionsFunction` → Fetches submissions (optional)  
- **DynamoDB Table**: `UserSubmissions`  
  - Partition Key: `SubmissionId`  
  - Attributes: `name`, `email`, `message`, `submissionDate`, `status`  

---

## 🗂 Project Structure

.
├── index.html # Frontend HTML + JS form
├── submit_lambda.py # Lambda function for POST /submit
├── query_lambda.py # Lambda function for GET /submissions (optional)
└── README.md

markdown
Copy code

---

## ⚙ Setup Instructions

### 1. DynamoDB Table
- Table name: `UserSubmissions`
- Partition key: `SubmissionId` (String)
- Other attributes are dynamic (schema-less)

### 2. Lambda Functions
- **SubmitFormFunction**
  - Trigger: API Gateway POST /submit
  - Permissions: `AmazonDynamoDBFullAccess`, `AWSLambdaBasicExecutionRole`
- **QuerySubmissionsFunction** (optional)
  - Trigger: API Gateway GET /submissions

### 3. API Gateway
- Create REST API with two resources:
  - `/submit` → POST → SubmitFormFunction
  - `/submissions` → GET → QuerySubmissionsFunction
- Enable **CORS** for both endpoints.
- Deploy the API and copy the **Invoke URL**.

### 4. EC2 Setup
- Launch **t2.micro/t3.micro** Amazon Linux instance.
- SSH into EC2 using your key pair.
- Install Apache:

```bash
sudo yum update -y
sudo yum install -y httpd
sudo systemctl start httpd
sudo systemctl enable httpd
Upload index.html to /var/www/html/:

bash
Copy code
sudo mv ~/index.html /var/www/html/
sudo chmod 644 /var/www/html/index.html
Update the API URL in index.html with your deployed API Gateway /submit URL.

💻 Testing
Open your EC2 public IP in a browser:

cpp
Copy code
http://<EC2-PUBLIC-IP>/
Fill out the form (Name, Email, Message) and submit.

Check DynamoDB → UserSubmissions table for new record.

Optionally, test /submissions endpoint to fetch all entries.
