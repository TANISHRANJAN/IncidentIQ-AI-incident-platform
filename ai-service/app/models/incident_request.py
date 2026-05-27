from pydantic import BaseModel
from typing import List


class IncidentRequest(BaseModel):
    incidentId: str
    title: str
    description: str
    logs: List[str] = []
    serviceName: str