from flask import current_app as app, request
from transformers import BertTokenizer, BertModel

import torch
import torch.nn.functional as F

model=BertModel.from_pretrained("hfl/minirbt-h288")
tokenizer=BertTokenizer.from_pretrained("hfl/minirbt-h288")

def cosine_similarity(text1, text2):
    return F.cosine_similarity(text1, text2, dim=-1)

def get_sentence_embedding(text):
    inputs=tokenizer(text,return_tensors="pt",padding=True,truncation=True)
    with torch.no_grad():
        outputs=model(**inputs)
        cls_embedding=outputs.pooler_output
    return cls_embedding

def calculate_similarity(text1, text2):
    embedding1=get_sentence_embedding(text1)
    embedding2=get_sentence_embedding(text2)
    return cosine_similarity(embedding1, embedding2)


@app.route('/similarity', methods=['GET'])
def index():
    text1=request.args.get('text1')
    text2=request.args.get('text2')
    similarity=calculate_similarity(text1, text2)
    similarity_value = f"{similarity.item():.2f}"  # 提取标量值
    return {'similarity': similarity_value}