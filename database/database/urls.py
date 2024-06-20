"""
URL configuration for database project.

The `urlpatterns` list routes URLs to Addresses. For more information please see:
    https://docs.djangoproject.com/en/5.0/topics/http/urls/
Examples:
Function Addresses
    1. Add an import:  from my_app import Addresses
    2. Add a URL to urlpatterns:  path('', Addresses.home, name='home')
Class-based Addresses
    1. Add an import:  from other_app.Addresses import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from horses_database.views import *


urlpatterns = [
    path('admin/', admin.site.urls),

    path('addresses/', AddressesView.get_all_addresses, name='get_all_addresses'),
    path('addresses/add/', AddressesView.add_addresses, name='addresses_add'),
    path('addresses/get_by_id/', AddressesView.get_address_by_id, name='addresses_get_by_id'),
    path('addresses/get_in_country/', AddressesView.get_all_addresses_in_country, name='addresses_get_in_country'),

    path('members/', MembersView.get_all_members, name='get_all_members'),
    path('members/add/', MembersView.add_members, name='members_add'),
    path('members/get_by_id/', MembersView.get_members_by_id, name='members_get_by_id'),
    path('members/get_in_country/', MembersView.get_all_members_in_country, name='members_get_in_country'),
    path('members/get_by_address_id/', MembersView.get_all_members_by_address_id, name='members_get_by_address_id'),
    path('members/get_active/', MembersView.get_all_active_members, name='get_all_active_members'),
    path('members/get_inactive/', MembersView.get_all_inactive_members, name='get_all_inactive_members'),

    path('accessories/', AccessoriesView.get_all_accessories, name='accessories'),
    path('accessories/add/', AccessoriesView.add_new_accessory, name='add_accessory'),
]
