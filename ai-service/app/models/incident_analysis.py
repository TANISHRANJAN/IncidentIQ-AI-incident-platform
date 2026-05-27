from pydantic import BaseModel
from typing import List


class IncidentAnalysis(BaseModel):
    summary: str
    severity: str
    category: str
    rootCause: str
    recommendations: List[str]