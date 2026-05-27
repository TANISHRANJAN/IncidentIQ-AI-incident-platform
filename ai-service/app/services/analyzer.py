from ollama import chat
import json

from app.models.incident_analysis import IncidentAnalysis
from app.models.incident_request import IncidentRequest


def analyze_incident(request: IncidentRequest):

    prompt = f"""
You are a senior Site Reliability Engineer.

Analyze this software production incident.

Incident ID: {request.incidentId}

Title:
{request.title}

Description:
{request.description}

Service:
{request.serviceName}

Logs:
{request.logs}

Return JSON only.

Example:

{{
  "summary":"...",
  "severity":"LOW | MEDIUM | HIGH | CRITICAL",
  "category":"Database | API | Network | Deployment | Payment | Unknown",
  "rootCause":"...",
  "recommendations":[
    "...",
    "..."
  ]
}}
"""

    response = chat(
        model="llama3.1:8b",
        messages=[
            {
                "role": "user",
                "content": prompt
            }
        ]
    )

    content = response["message"]["content"]

    try:
        parsed = json.loads(content)

        return IncidentAnalysis(
            summary=parsed["summary"],
            severity=parsed["severity"],
            category=parsed["category"],
            rootCause=parsed["rootCause"],
            recommendations=parsed["recommendations"],
        )

    except Exception:

        return IncidentAnalysis(
            summary="AI analysis failed",
            severity="MEDIUM",
            category="Unknown",
            rootCause="Could not parse LLM output",
            recommendations=[
                "Check Ollama response",
                "Review prompt formatting",
            ],
        )