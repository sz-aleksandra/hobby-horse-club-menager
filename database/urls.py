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
from horses_database.views.accessories_view import *
from horses_database.views.addresses_view import *
from horses_database.views.licences_view import *
from horses_database.views.groups_view import *
from horses_database.views.accessory_types_view import *
from horses_database.views.horses_view import *
from horses_database.views.stables_view import *
from horses_database.views.positions_view import *
from horses_database.views.members_view import *
from horses_database.views.riders_view import *
from horses_database.views.employees_view import *
from horses_database.views.tournaments_view import *
from horses_database.views.classes_view import *
from horses_database.views.tournament_participants_view import *
from horses_database.views.horses_accessories_view import *
from horses_database.views.positions_history_view import *


urlpatterns = [
    path('admin/', admin.site.urls),

    path('accessories/', AccessoriesView.get_all_accessories, name='get_all_accessories'),
    path('accessories/get_all/', AccessoriesView.get_all_accessories, name='get_all_accessories'),
    path('accessories/get_by_id/', AccessoriesView.get_accessory_by_id, name='get_accessory_by_id'),
    path('accessories/add/', AccessoriesView.add_new_accessories, name='add_accessory'),
    path('accessories/update/', AccessoriesView.update_accessories, name='update_accessory'),
    path('accessories/delete/', AccessoriesView.delete_accessories, name='delete_accessory'),

    path('addresses/', AddressesView.get_all_addresses, name='get_all_addresses'),
    path('addresses/get_all/', AddressesView.get_all_addresses, name='get_all_addresses'),
    path('addresses/get_by_id/', AddressesView.get_address_by_id, name='get_address_by_id'),
    path('addresses/get_in_country/', AddressesView.get_all_addresses_in_country, name='get_all_addresses_in_country'),
    path('addresses/get_in_city/', AddressesView.get_all_addresses_in_city, name='get_all_addresses_in_city'),
    path('addresses/get_in_street/', AddressesView.get_all_addresses_in_street, name='get_all_addresses_in_street'),
    path('addresses/add/', AddressesView.add_addresses, name='add_address'),
    path('addresses/update/', AddressesView.update_addresses, name='update_address'),
    path('addresses/delete/', AddressesView.delete_addresses, name='delete_address'),

    path('licences/', LicencesView.get_all_licences, name='get_all_licences'),
    path('licences/get_all/', LicencesView.get_all_licences, name='get_all_licences'),
    path('licences/get_by_id/', LicencesView.get_licence_by_id, name='get_licence_by_id'),
    path('licences/add/', LicencesView.add_new_licence, name='add_licence'),
    path('licences/update/', LicencesView.update_licence, name='update_licence'),
    path('licences/delete/', LicencesView.delete_licence, name='delete_licence'),

    path('groups/', GroupsView.get_all_groups, name='get_all_groups'),
    path('groups/get_all/', GroupsView.get_all_groups, name='get_all_groups'),
    path('groups/get_by_id/', GroupsView.get_group_by_id, name='get_group_by_id'),
    path('groups/add/', GroupsView.add_new_group, name='add_group'),
    path('groups/update/', GroupsView.update_group, name='update_group'),
    path('groups/delete/', GroupsView.delete_group, name='delete_group'),

    path('accessory_types/', AccessoryTypesView.get_all_accessory_types, name='get_all_accessory_types'),
    path('accessory_types/get_all/', AccessoryTypesView.get_all_accessory_types, name='get_all_accessory_types'),
    path('accessory_types/get_by_id/', AccessoryTypesView.get_accessory_types_by_id, name='get_accessory_types_by_id'),
    path('accessory_types/add/', AccessoryTypesView.add_accessory_type, name='add_accessory_type'),
    path('accessory_types/update/', AccessoryTypesView.update_accessory_type, name='update_accessory_type'),
    path('accessory_types/delete/', AccessoryTypesView.delete_accessory_type, name='delete_accessory_type'),

    path('horses/', HorsesView.get_all_horses, name='get_all_horses'),
    path('horses/get_all/', HorsesView.get_all_horses, name='get_all_horses'),
    path('horses/get_by_id/', HorsesView.get_horses_by_id, name='get_horse_by_id'),
    path('horses/add/', HorsesView.add_horse, name='add_horse'),
    path('horses/update/', HorsesView.update_horse, name='update_horse'),
    path('horses/delete/', HorsesView.delete_horse, name='delete_horse'),

    path('stables/', StablesView.get_all_stables, name='get_all_stables'),
    path('stables/get_all/', StablesView.get_all_stables, name='get_all_stables'),
    path('stables/get_by_id/', StablesView.get_stable_by_id, name='get_stable_by_id'),
    path('stables/add/', StablesView.add_stable, name='add_stable'),
    path('stables/update/', StablesView.update_stable, name='update_stable'),
    path('stables/delete/', StablesView.delete_stable, name='delete_stable'),

    path('positions/', PositionsView.get_all_positions, name='get_all_positions'),
    path('positions/get_all/', PositionsView.get_all_positions, name='get_all_positions'),
    path('positions/get_by_id/', PositionsView.get_position_by_id, name='get_position_by_id'),
    path('positions/add/', PositionsView.add_position, name='add_position'),
    path('positions/update/', PositionsView.update_position, name='update_position'),
    path('positions/delete/', PositionsView.delete_positions, name='delete_positions'),

    path('members/', MembersView.get_all_members, name='get_all_members'),
    path('members/get_all/', MembersView.get_all_members, name='get_all_members'),
    path('members/get_by_id/', MembersView.get_member_by_id, name='get_member_by_id'),
    path('members/add/', MembersView.add_member, name='add_member'),
    path('members/update/', MembersView.update_member, name='update_member'),
    path('members/delete/', MembersView.delete_member, name='delete_member'),

    path('riders/', RidersView.get_all_riders, name='get_all_riders'),
    path('riders/get_all', RidersView.get_all_riders, name='get_all_riders'),
    path('riders/get_by_id/', RidersView.get_rider_by_id, name='get_rider_by_id'),
    path('riders/add/', RidersView.add_rider, name='add_rider'),
    path('riders/update/', RidersView.update_rider, name='update_rider'),
    path('riders/delete/', RidersView.delete_rider, name='delete_rider'),
    path('riders/login_rider/', RidersView.login_rider, name='login_rider'),

    path('employees/', EmployeesView.get_all_employees, name='get_all_employees'),
    path('employees/get_all', EmployeesView.get_all_employees, name='get_all_employees'),
    path('employees/get_by_id/', EmployeesView.get_employee_by_id, name='get_employee_by_id'),
    path('employees/add/', EmployeesView.add_employee, name='add_employee'),
    path('employees/update/', EmployeesView.update_employee, name='update_employee'),
    path('employees/delete/', EmployeesView.delete_employee, name='delete_employee'),
    path('employees/login_employee/', EmployeesView.login_employee, name='login_employee'),
    path('employees/login_owner/', EmployeesView.login_owner, name='login_owner'),

    path('tournaments/', TournamentsView.get_all_tournaments, name='get_all_tournaments'),
    path('tournaments/get_all', TournamentsView.get_all_tournaments, name='get_all_tournaments'),
    path('tournaments/get_by_id/', TournamentsView.get_tournament_by_id, name='get_tournament_by_id'),
    path('tournaments/add/', TournamentsView.add_tournament, name='add_tournament'),
    path('tournaments/update/', TournamentsView.update_tournament, name='update_tournament'),
    path('tournaments/delete/', TournamentsView.delete_tournament, name='delete_tournament'),

    path('classes/', ClassesView.get_all_classes, name='get_all_classes'),
    path('classes/get_all', ClassesView.get_all_classes, name='get_all_classes'),
    path('classes/get_by_id', ClassesView.get_class_by_id, name='get_class_by_id'),
    path('classes/add/', ClassesView.add_class, name='add_class'),
    path('classes/update/', ClassesView.update_class, name='update_class'),
    path('classes/delete/', ClassesView.delete_class, name='delete_class'),

    path('tournament_participants/', TournamentParticipantsView.get_all_tournaments_participants, name='get_all_tournaments_participants'),
    path('tournament_participants/get_all', TournamentParticipantsView.get_all_tournaments_participants, name='get_all_tournaments_participants'),
    path('tournament_participants/get_by_id', TournamentParticipantsView.get_tournament_participant_by_id, name='get_tournament_participant_by_id'),
    path('tournament_participants/add/', TournamentParticipantsView.add_tournament, name='add_tournament_participant'),
    path('tournament_participants/update/', TournamentParticipantsView.update_tournament, name='update_tournament_participant'),
    path('tournament_participants/delete/', TournamentParticipantsView.delete_tournament_participant, name='delete_tournament_participant'),

    path('horses_accessories/', HorsesAccessoriesView.get_all_horses_accessories, name='get_all_horses_accessories'),
    path('horses_accessories/get_all', HorsesAccessoriesView.get_all_horses_accessories, name='get_all_horses_accessories'),
    path('horses_accessories/get_by_id', HorsesAccessoriesView.get_horses_accessories_by_id, name='get_horses_accessory_by_id'),
    path('horses_accessories/add/', HorsesAccessoriesView.add_horses_accessory, name='add_horses_accessory'),
    path('horses_accessories/update/', HorsesAccessoriesView.update_horse_accessory, name='update_horses_accessory'),
    path('horses_accessories/delete/', HorsesAccessoriesView.delete_horses_accessory, name='delete_horses_accessory'),

    path('positions_history/', PositionsHistoryView.get_all_positions_history, name='get_all_positions_history'),
    path('positions_history/get_all', PositionsHistoryView.get_all_positions_history, name='get_all_positions_history'),
    path('positions_history/get_by_id', PositionsHistoryView.get_position_history_by_id, name='get_position_history_by_id'),
    path('positions_history/add/', PositionsHistoryView.add_position_history, name='add_position_history'),
    path('positions_history/update/', PositionsHistoryView.update_position_history, name='update_position_history'),
    path('positions_history/delete/', PositionsHistoryView.delete_positions_history, name='delete_position_history'),
]
