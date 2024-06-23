from django.test import TestCase
from django.urls import reverse
from horses_database.models import Riders, Employees, Members, Tournaments, Addresses, Groups, Positions, Licences, Horses, Accessories, AccessoryTypes, Stables, TournamentParticipants


class TournamentViewsTestCase(TestCase):

    def setUp(self):
        self.licence_a = Licences.objects.create(licence_level='A')
        self.licence_advanced = Licences.objects.create(licence_level='Advanced')
        self.licence_basic = Licences.objects.create(licence_level='Basic')
        self.address = Addresses.objects.create(
            country='USA',
            city='New York',
            street='Broadway',
            street_no='123',
            postal_code='10001'
        )

        self.group = Groups.objects.create(name='Group A', max_group_members=10)
        self.horse = Horses.objects.create(
            breed='Thoroughbred',
            height=1.75,
            color='Bay',
            eye_color='Brown',
            age=6,
            origin='USA',
            hairstyle='Short'
        )

        self.member = Members.objects.create(
            name='John',
            surname='Doe',
            username='johndoe',
            password='password',
            date_of_birth='1990-01-01',
            address=self.address,
            phone_number='+1234567890',
            email='john.doe@example.com',
            is_active=True,
            licence=self.licence_a
        )

        self.member_emp = Members.objects.create(
            name='John',
            surname='Doe',
            username='johndoe',
            password='password',
            date_of_birth='1990-01-01',
            address=self.address,
            phone_number='+1234567890',
            email='john.doe@example.com',
            is_active=True,
            licence=self.licence_a
        )

        self.rider = Riders.objects.create(
            member=self.member,
            parent_consent=True,
            group=self.group,
            horse=self.horse
        )

        self.trainer = Employees.objects.create(
            member=self.member_emp,
            position=Positions.objects.create(
                name='Head Coach',
                salary_min=5000.00,
                salary_max=8000.00,
                licence=self.licence_advanced,
                coaching_licence=self.licence_basic,
                speciality='Jumping'
            ),
            salary=1000,
            date_employed='1985-05-15'
        )

        self.tournament = Tournaments.objects.create(
            name='Championship',
            date='1990-01-01',
            address=self.address,
            judge=self.trainer
        )

        TournamentParticipants.objects.create(
            tournament=self.tournament,
            contestant=self.rider,
            contestant_place=1
        )

    def test_get_tournaments_for_rider_success(self):
        url = reverse('get_tournaments_for_rider')
        data = {'id': self.rider.id}
        response = self.client.post(url, data, content_type='application/json')
        self.assertEqual(response.status_code, 200)
        self.assertIn('tournaments', response.json())

    def test_get_tournaments_for_rider_missing_id(self):
        url = reverse('get_tournaments_for_rider')
        data = {}
        response = self.client.post(url, data, content_type='application/json')
        self.assertEqual(response.status_code, 400)
        self.assertIn('error', response.json())

    def test_get_tournaments_for_rider_invalid_id(self):
        url = reverse('get_tournaments_for_rider')
        data = {'id': 999}
        response = self.client.post(url, data, content_type='application/json')
        self.assertEqual(response.status_code, 401)
        self.assertIn('error', response.json())

    def test_get_tournaments_for_rider_invalid_json(self):
        url = reverse('get_tournaments_for_rider')
        data = 'invalid json'
        response = self.client.post(url, data, content_type='application/json')
        self.assertEqual(response.status_code, 400)
        self.assertIn('error', response.json())

    def test_get_tournaments_for_employee_success(self):
        url = reverse('get_tournaments_for_employee')
        data = {'id': self.trainer.id}
        response = self.client.post(url, data, content_type='application/json')
        self.assertEqual(response.status_code, 200)
        self.assertIn('tournaments', response.json())

    def test_get_tournaments_for_employee_missing_id(self):
        url = reverse('get_tournaments_for_employee')
        data = {}
        response = self.client.post(url, data, content_type='application/json')
        self.assertEqual(response.status_code, 400)
        self.assertIn('error', response.json())

    def test_get_tournaments_for_employee_invalid_id(self):
        url = reverse('get_tournaments_for_employee')
        data = {'id': 999}
        response = self.client.post(url, data, content_type='application/json')
        self.assertEqual(response.status_code, 401)
        self.assertIn('error', response.json())

    def test_get_tournaments_for_employee_invalid_json(self):
        url = reverse('get_tournaments_for_employee')
        data = 'invalid json'
        response = self.client.post(url, data, content_type='application/json')
        self.assertEqual(response.status_code, 400)
        self.assertIn('error', response.json())
