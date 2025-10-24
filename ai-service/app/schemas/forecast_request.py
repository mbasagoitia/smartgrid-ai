from pydantic import BaseModel
from typing import List, Dict

# This sends data to the ai service, requesting predicted output for this configuration and weather
# and/or optimal placement of the grid nodes
class ForecastRequest(BaseModel):
    # One of the four specificed regions
    region_id: str
    # Recent weather data
    weather_data: List[Dict[str, float]]
    # Current output
    current_output_mw: float
    # Max capacity
    capacity_mw: float
    # Terrain at a given placement, think about how to simulate this (by coords? region? randomly?)
    terrain: str
    # Distance from the city being powered
    distance: float
    # Transmission loss due to distance between generators
    transmission_loss: float
