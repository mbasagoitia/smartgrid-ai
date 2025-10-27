
from fastapi import APIRouter
from ..schemas.forecast_request import ForecastRequest
from ..schemas.forecast_response import ForecastResponse
from ..services.forecast_service import ForecastService

router = APIRouter()
service = ForecastService()

@router.post('/predict', response_model=ForecastResponse)
def predict_forecast(req: ForecastRequest):
    # returns mock forecast for the provided placement
    return service.predict(req)
