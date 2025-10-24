from pydantic import BaseModel
from typing import List, Dict

# This sends data to the ai service, requesting predicted output for this configuration and weather
# and/or optimal placement of the grid nodes
class ForecastRequest(BaseModel):
    region_id: str
    weather_data: List[Dict[str, float]]
    current_output_mw: float
    capacity_mw: float
