from fastapi import FastAPI
from app.api.incident_routes import router

app = FastAPI()

app.include_router(router)