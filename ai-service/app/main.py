from fastapi import FastAPI
from app.routes import forecast_router

app = FastAPI()

app.include_router(forecast_router.router, prefix="/forecast", tags=["Forecast"])
