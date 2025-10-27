
from typing import List
from ..schemas.forecast_request import ForecastRequest, GeneratorPlacement
from ..schemas.forecast_response import ForecastResponse, CountyForecast
import random

class ForecastService:
    def predict(self, req: ForecastRequest) -> ForecastResponse:
        # Simple mock: generate synthetic monthly profiles per county
        county_map = {}
        for p in req.placements:
            if p.county not in county_map:
                county_map[p.county] = 0
            if p.type.lower() == 'solar':
                county_map[p.county] += p.count * 50 * 0.25  # MW * capacity factor -> MW avg
            elif p.type.lower() == 'wind':
                county_map[p.county] += p.count * 100 * 0.35
            elif p.type.lower() == 'hydro':
                county_map[p.county] += p.count * 200 * 0.5
            elif p.type.lower() == 'nuclear':
                county_map[p.county] += p.count * 1000 * 0.9
        county_forecasts = []
        total = 0.0
        for county, avg_mw in county_map.items():
            # make simple seasonal curve across 12 months
            monthly = []
            for m in range(12):
                season_factor = 1.0 + 0.2 * (0.5 - abs((m-6)/6.0))  # simple bell-ish
                gen_mwh = avg_mw * 24 * season_factor * 30/30.0  # approx per-month MWh
                monthly.append(round(gen_mwh,2))
                total += gen_mwh
            county_forecasts.append(CountyForecast(county=county, monthly_generation_mwh=monthly))
        return ForecastResponse(state=req.state, total_annual_generation_mwh=round(total,2), county_forecasts=county_forecasts, message='Mock forecast generated')
