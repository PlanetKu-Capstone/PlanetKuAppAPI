from flask import Flask, request, jsonify, make_response
from flask_cors import CORS
import papermill as pm
import os

# Inisialisasi Flask
app = Flask(__name__)
CORS(app)

# Jalur file .ipynb
NOTEBOOK_PATH = "./notebooks/GarbageClassification.ipynb"
OUTPUT_NOTEBOOK_PATH = "./notebooks/GarbageClassification_output.ipynb"

@app.route('/predict-sampah', methods=['POST'])
def classify_waste():
    if 'file' not in request.files:
        return jsonify({'error': 'File tidak ditemukan'}), 400

    try:
        # Simpan gambar sementara
        image_file = request.files['file']
        temp_image_path = "./temp_image.jpg"
        image_file.save(temp_image_path)

        # Jalankan file .ipynb menggunakan papermill
        result = pm.execute_notebook(
            NOTEBOOK_PATH,
            OUTPUT_NOTEBOOK_PATH,
            parameters={"image_path": temp_image_path}
        )

        # Ambil hasil dari notebook output
        prediction = result['predicted_class']
        confidence = result['confidence']

        response = {
            'code': 200,
            'message': 'Berhasil memprediksi!',
            'prediksi': prediction,
            'confidence': f"{confidence:.2%}"
        }
        return make_response(jsonify(response)), 200

    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == "__main__":
    app.run(port=int(os.environ.get("PORT", 8080)), host='0.0.0.0', debug=True)
