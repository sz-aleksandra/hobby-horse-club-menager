from django.test import TestCase
from django.urls import reverse
import json
from horses_database.models import Members, Addresses, Licences


class MembersViewTests(TestCase):

    def setUp(self):
        # Create sample data for testing
        self.address1 = Addresses.objects.create(country="USA", city="New York", street="Broadway", street_no="123", postal_code="10001")
        self.address2 = Addresses.objects.create(country="Canada", city="Toronto", street="King Street", street_no="456", postal_code="M5V 1J4")
        self.licence1 = Licences.objects.create(licence_level="Advanced")
        self.licence2 = Licences.objects.create(licence_level="Basic")
        self.member1 = Members.objects.create(name="John", surname="Doe", username="johndoe", password="secure_password", date_of_birth="1990-01-01", address=self.address1, phone_number="+1234567890", email="john.doe@example.com", is_active=True, licence=self.licence1)
        self.member2 = Members.objects.create(name="Jane", surname="Smith", username="janesmith", password="strong_password", date_of_birth="1985-05-15", address=self.address2, phone_number="+9876543210", email="jane.smith@example.com", is_active=True, licence=self.licence2)

    def test_get_all_members(self):
        url = reverse('get_all_members')
        response = self.client.get(url)
        self.assertEqual(response.status_code, 200)
        members_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('members', members_data)
        members = members_data['members']
        self.assertEqual(len(members), 2)

    def test_get_member_by_id(self):
        url = reverse('get_member_by_id')
        data = {'ids': [self.member1.id]}
        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        members_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('members', members_data)
        members = members_data['members']
        self.assertEqual(len(members), 1)
        member = members[0]
        self.assertEqual(member['id'], self.member1.id)

    def test_add_member(self):
        url = reverse('add_member')
        new_member_data = {
            "members": [
                {
                    "name": "Jack",
                    "surname": "Black",
                    "username": "jackblack",
                    "password": "strong_password",
                    "date_of_birth": "1980-06-20",
                    "address": {
                        "country": "UK",
                        "city": "London",
                        "street": "Oxford Street",
                        "street_no": "789",
                        "postal_code": "W1D 1AB"
                    },
                    "phone_number": "+447123456789",
                    "email": "jack.black@example.com",
                    "is_active": True,
                    "licence": {
                        "id": self.licence1.id
                    }
                }
            ]
        }

        response = self.client.post(url, json.dumps(new_member_data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        response_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('ids', response_data)
        new_member_id = response_data['ids'][0]
        self.assertTrue(Members.objects.filter(id=new_member_id).exists())

    def test_update_member(self):
        url = reverse('update_member')
        updated_member_data = {
            "members": [
                {
                    "id": self.member1.id,
                    "name": "John",
                    "surname": "Doe",
                    "username": "johndoe_updated",
                    "password": "qwerty",
                    "date_of_birth": "1990-01-01",
                    "address": {
                        "id": self.address1.id,
                        "country": "USA",
                        "city": "New York",
                        "street": "Broadway",
                        "street_no": "123",
                        "postal_code": "10001"
                    },
                    "phone_number": "+1234567890",
                    "email": "john.doe@example.com",
                    "is_active": True,
                    "licence": {
                        "id": self.licence1.id
                    }
                }
            ]
        }

        response = self.client.post(url, json.dumps(updated_member_data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        response_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('ids', response_data)
        updated_member_id = response_data['ids'][0]
        self.assertEqual(updated_member_id, self.member1.id)

    def test_delete_member(self):
        url = reverse('delete_member')
        data = {'ids': [self.member1.id]}
        response = self.client.post(url, json.dumps(data), content_type='application/json')
        self.assertEqual(response.status_code, 200)
        response_data = json.loads(response.content.decode('utf-8'))
        self.assertIn('ids', response_data)
        deleted_member_id = response_data['ids'][0]
        self.assertFalse(Members.objects.filter(id=deleted_member_id).exists())
