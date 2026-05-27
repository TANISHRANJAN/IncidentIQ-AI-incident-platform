from fastapi import APIRouter
from app.models.incident_request import IncidentRequest
from app.services.analyzer import analyze_incident

router = APIRouter()


@router.get("/health")
def health():
    return {"status": "UP"}


@router.post("/analyze")
def analyze(request: IncidentRequest):
    return analyze_incident(request)