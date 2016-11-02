import pika
from pymongo import MongoClient

# MongoDB connection
client = MongoClient('mongodb://localhost:27017')
db = client['meirlbot_mongodb']
rposts = db.redditposts


connection = pika.BlockingConnection(pika.ConnectionParameters(
        host='localhost'))
# Remove
removeChannel = connection.channel()

removeChannel.exchange_declare(exchange='database',type='direct')

result = removeChannel.queue_declare(exclusive=True)
queue_name = result.method.queue

removeChannel.queue_bind(exchange='database', queue=queue_name, routing_key='remove')

print('  [*] Waiting for database instructions. To exit press CTRL+C')

def removeCallback(ch, method, properties, body):
    print("  sf[x] Received: %r" % body)
    try:
        data = json.loads(body)
    except:
        print("ERROR")
    print("ID: {}".format(data['id']))
    print("Name: {}".format(data['name']))
    print('Description: {}'.format(data['description']))

removeChannel.basic_consume(removeCallback, queue=queue_name,no_ack=True)


# Update
updateChannel = connection.channel()

updateChannel.exchange_declare(exchange='database',type='direct')

result = updateChannel.queue_declare(exclusive=True)
queue_name = result.method.queue

updateChannel.queue_bind(exchange='database', queue=queue_name, routing_key='update')

print('  [*] Waiting for database instructions. To exit press CTRL+C')

def updateCallback(ch, method, properties, body):
    print(" [l] Received: %r" % body)
    data = json.loads(body)
    print("ID: {}".format(data['id']))
    print("Name: {}".format(data['name']))
    print('Description: {}'.format(data['description']))

updateChannel.basic_consume(updateCallback, queue=queue_name,no_ack=True)

updateChannel.start_consuming()
removeChannel.start_consuming()