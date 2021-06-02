import numpy as np
import json
import skimage
import cv2
import keras
import urllib.request


from skimage.transform import resize
from keras.models import load_model
from flask import Flask, request, jsonify
from skimage.util.dtype import convert

#init flask
app = Flask(__name__)

#load model
model = load_model('ASL_transfer_learning.h5')
alphabet=['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','del','nothing','space']

def convert_img(img):
    request = urllib.request.urlretrieve(img, "image.png")

    #read image
    img_file = cv2.imread("image.png")
    img_file = skimage.transform.resize(img_file, (75, 75, 3))
    img_arr = np.asarray(img_file).reshape((-1, 75, 75, 3))
    return img_arr

@app.route("/")
def hello():

    # img = cv2.imread("ASL A.jpg")
    # img_file = skimage.transform.resize(img, (75, 75, 3))
    # img_arr = np.asarray(img_file).reshape((-1, 75, 75, 3))

    # proba = model.predict(img_arr)
    # idx = np.argmax(proba)

    # return alphabet[idx]
    return "wellcome to sign language classification API,use json ({data: https://firebase/picture-link})"

@app.route("/predict", methods=["POST"])
def predict():
    data = request.get_json()
    img_arr = convert_img(data['url'])

    proba = model.predict(img_arr)
    idx = np.argmax(proba)

    return jsonify({'result': alphabet[idx]})

if __name__ == '__main__' :

    app.run(host='0.0.0.0', port=5000)
