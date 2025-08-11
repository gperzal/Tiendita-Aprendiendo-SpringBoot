import yaml
import os

# Cambiar BASE_PATH y OUTPUT_FILE para que sean relativos al directorio del script
SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
BASE_PATH = SCRIPT_DIR  # Ya estás en .../openapi
OUTPUT_FILE = os.path.join(SCRIPT_DIR, "../static/openapi.yaml")


combined = {
    "openapi": "3.1.0",
    "info": {
        "title": "Tiendita API",
        "version": "1.0.0",
        "description": "Documentación combinada de los módulos de la API"
    },
    "paths": {},
    "components": {
        "schemas": {}
    }
}

if not os.path.exists(BASE_PATH):
    raise FileNotFoundError(f"La ruta especificada no existe: {BASE_PATH}")

for filename in os.listdir(BASE_PATH):
    if filename.endswith(".yaml") and filename != "openapi.yaml":
        filepath = os.path.join(BASE_PATH, filename)
        with open(filepath, "r", encoding="utf-8") as f:
            data = yaml.safe_load(f)
            if not data:
                continue
            if "paths" in data:
                combined["paths"].update(data["paths"])
            if "components" in data and "schemas" in data["components"]:
                combined["components"]["schemas"].update(data["components"]["schemas"])

os.makedirs(os.path.dirname(OUTPUT_FILE), exist_ok=True)

with open(OUTPUT_FILE, "w", encoding="utf-8") as f:
    yaml.dump(combined, f, sort_keys=False, allow_unicode=True)

print(f"✅ Documentación combinada generada en: {OUTPUT_FILE}")