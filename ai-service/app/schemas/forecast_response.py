
from pydantic import BaseModel
from typing import Dict, Any, List

class CountyForecast(BaseModel):
    county: str
    monthly_generation_mwh: List[float]

class ForecastResponse(BaseModel):
    state: str
    total_annual_generation_mwh: float
    county_forecasts: List[CountyForecast]
    message: str = ''
