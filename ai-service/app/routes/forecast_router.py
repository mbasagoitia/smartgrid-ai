from fastapi import APIRouter
from app.schemas.forecast_request import ForecastRequest
from app.schemas.forecast_response import ForecastResponse
from app.services.forecast_service import ForecastService

router = APIRouter()
service = ForecastService()

@router.post("/", response_model=ForecastResponse)
async def get_forecast(request: ForecastRequest):
    result = service.predict(request)
    return result
