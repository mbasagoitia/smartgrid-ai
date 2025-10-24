from pydantic import BaseModel

class ForecastResponse(BaseModel):
    predicted_output_mw: float
    recommended_placement: str