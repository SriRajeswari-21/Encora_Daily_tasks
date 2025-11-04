import json
 
def lambda_handler(event, context):
    premium_orders = []
    # EventBridge Pipes sends a list of records
    if isinstance(event, list):
        records = event
    else:
        records = [event]
    for record in records:
        # DynamoDB Stream structure
        dynamodb = record.get('dynamodb', {})
        new = dynamodb.get('NewImage', {})
        old = dynamodb.get('OldImage', {})
 
        # Extract values safely
        order_id = new.get('orderId', {}).get('S')
        new_status = new.get('status', {}).get('S')
        old_status = old.get('status', {}).get('S')
        amount_str = new.get('amount', {}).get('N', '0')
        customer_email = new.get('customerEmail', {}).get('S', '')
 
        try:
            amount = float(amount_str)
        except ValueError:
            amount = 0
 
        # Filtering logic
        if (
            old_status == "pending" and
            new_status == "shipped" and
            amount > 1000 and
            "test.com" not in customer_email.lower()
        ):
            print(f"🚀 Premium order shipped! Order ID: {order_id} | Amount={amount}")
            premium_orders.append(order_id)
        else:
            print(f"Skipping order {order_id} | Amount={amount}, Status {old_status}->{new_status}, Email={customer_email}")
 
    return {
        "statusCode": 200,
        "premiumOrdersProcessed": premium_orders
    }
 
