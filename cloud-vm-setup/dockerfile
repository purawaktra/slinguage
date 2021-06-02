FROM python:3.7.2
COPY . /app
WORKDIR /app
RUN pip install -r requirements.txt
RUN pip install opencv-python
RUN pip install tensorflow
EXPOSE 9090
ENTRYPOINT ["python"]
CMD ["main.py"]