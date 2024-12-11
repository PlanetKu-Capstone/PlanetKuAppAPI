from flask import Flask, request, jsonify
import pickle
import numpy as np

# Inisialisasi Flask
app = Flask(__name__)

# Muat Model
MODEL_PATH = 'model/carbon_emission.pkl'
with open(MODEL_PATH, 'rb') as model_file:
    model = pickle.load(model_file)

@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Ambil data dari permintaan
        data = request.json

        # Validasi data
        required_fields = ['electricity', 'gas', 'transportation', 'food', 'organic_waste', 'inorganic_waste']
        if not all(field in data for field in required_fields):
            return jsonify({'error': 'Data tidak lengkap'}), 400

        # Prediksi
        features = np.array([[data[field] for field in required_fields]])
        prediction = model.predict(features)

        # Kirim respon
        response = {
            'predicted_carbon_footprint': float(prediction[0])
        }
        return jsonify(response), 200

    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True, port=8080)
