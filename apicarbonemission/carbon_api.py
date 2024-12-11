from flask import Flask, request, jsonify
import numpy as np
import pickle

# Inisialisasi Flask
app = Flask(__name__)

# Muat model yang disimpan
with open('carbon_emission.pkl', 'rb') as model_file:
    model = pickle.load(model_file)

@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Ambil data dari request JSON
        data = request.json
        
        # Persiapkan fitur untuk prediksi
        features = np.array([[data['electricity'], data['gas'], data['transportation'], 
                              data['food'], data['organic_waste'], data['inorganic_waste']]])
        
        # Lakukan prediksi
        prediction = model.predict(features)
        
        return jsonify({
            'predicted_carbon_footprint': round(prediction[0], 2)
        })
    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True, port=8080)
