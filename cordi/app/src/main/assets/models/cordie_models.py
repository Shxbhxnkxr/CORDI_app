# Install libraries
!pip install transformers datasets tensorflow

# Restart the runtime here manually if necessary

# Login to Hugging Face
!huggingface-cli login

# Import libraries
from transformers import TFBertForMaskedLM, BertTokenizer
import tensorflow as tf
import numpy as np

# Load the tokenizer and model
tokenizer = BertTokenizer.from_pretrained('bert-base-uncased')
model = TFBertForMaskedLM.from_pretrained('bert-base-uncased')

# Save the model
model.save_pretrained('./saved_model')

# Initialize the TFLite converter
converter = tf.lite.TFLiteConverter.from_saved_model('./saved_model')

# Set optimization (optional)
converter.optimizations = [tf.lite.Optimize.DEFAULT]

# Convert the model
tflite_model = converter.convert()

# Save the TFLite model
with open('bert_base_uncased.tflite', 'wb') as f:
    f.write(tflite_model)

# Load the TFLite model
interpreter = tf.lite.Interpreter(model_path='bert_base_uncased.tflite')

# Allocate tensors
interpreter.allocate_tensors()

# Get input and output details
input_details = interpreter.get_input_details()
output_details = interpreter.get_output_details()

# Prepare the input text
input_text = "The capital of France is [MASK]."

# Encode the input text
inputs = tokenizer.encode_plus(
    input_text,
    return_tensors='np'  # Return as NumPy arrays
)

# Get input IDs and attention masks
input_ids = inputs['input_ids']
attention_mask = inputs['attention_mask']

# Set the input tensors
interpreter.set_tensor(input_details[0]['index'], input_ids)
# Set attention mask if required
if len(input_details) > 1:
    interpreter.set_tensor(input_details[1]['index'], attention_mask)

# Run the interpreter
interpreter.invoke()

# Get the output probabilities
output = interpreter.get_tensor(output_details[0]['index'])

# Find the predicted token ID at the masked position
masked_index = np.where(input_ids[0] == tokenizer.mask_token_id)[0][0]
predicted_token_id = np.argmax(output[0][masked_index])

# Decode the predicted token
predicted_token = tokenizer.decode([predicted_token_id])

print("Predicted token:", predicted_token)
