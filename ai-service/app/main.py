
from fastapi import FastAPI
from .routes.forecast_router import router as forecast_router

app = FastAPI(title='SmartGrid AI Service')
app.include_router(forecast_router, prefix='/forecast')
