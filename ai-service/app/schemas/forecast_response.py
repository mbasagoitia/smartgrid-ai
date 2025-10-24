from pydantic import BaseModel

# Returns predicted output for a given weather and node configuration and recommended node placement
class ForecastResponse(BaseModel):
    predicted_output_mw: float
    recommended_placement: str