from django.shortcuts import render
from django.http import JsonResponse
from horses_database.models import *

# Create your views here.


def get_address(request):
    addresses = list(Address.objects.all().values())  # Pobierz wszystkie obiekty i przekształć do listy słowników
    return JsonResponse({'addresses': addresses}, status=200)
