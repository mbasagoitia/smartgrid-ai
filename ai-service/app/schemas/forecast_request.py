from pydantic import BaseModel
from typing import List, Dict

class ForecastRequest(BaseModel):
    region_id: str
    weather_data: List[Dict[str, float]]
    current_output_mw: float
    capacity_mw: float
