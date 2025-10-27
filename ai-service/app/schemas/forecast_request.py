
from pydantic import BaseModel
from typing import List, Dict

class GeneratorPlacement(BaseModel):
    type: str
    county: str
    count: int

class ForecastRequest(BaseModel):
    state: str
    placements: List[GeneratorPlacement]
    battery_strategy: str
