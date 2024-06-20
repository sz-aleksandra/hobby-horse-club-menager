from django.db import models

class Accessories(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=255)

    class Meta:
        db_table = 'Accessories'

class Addresses(models.Model):
    id = models.AutoField(primary_key=True)
    country = models.CharField(max_length=255)
    city = models.CharField(max_length=255)
    street = models.CharField(max_length=255)

    class Meta:
        db_table = 'Addresses'

class Members(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=255)
    surname = models.CharField(max_length=255)
    date_of_birth = models.DateField()
    address = models.ForeignKey(Addresses, on_delete=models.CASCADE)
    phone_number = models.CharField(max_length=20)
    email = models.EmailField()
    is_active = models.BooleanField(default=True)

    class Meta:
        db_table = 'Members'

class Riders(models.Model):
    id = models.AutoField(primary_key=True)
    parent_consent = models.CharField(max_length=50)
    licence = models.CharField(max_length=100)
    group_id = models.IntegerField()
    horse_id = models.IntegerField()
    member = models.ForeignKey(Members, on_delete=models.CASCADE)

    class Meta:
        db_table = 'Riders'

class Employees(models.Model):
    id = models.AutoField(primary_key=True)
    position_id = models.IntegerField()
    salary = models.FloatField()
    date_employed = models.DateField()
    member = models.ForeignKey(Members, on_delete=models.CASCADE)

    class Meta:
        db_table = 'Employees'

class Positions(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=255)
    salary_min = models.FloatField()
    salary_max = models.FloatField()
    licence_id = models.IntegerField()
    coaching_licence_id = models.IntegerField(null=True)
    speciality = models.CharField(max_length=255)

    class Meta:
        db_table = 'Positions'

class Positions_History(models.Model):
    id = models.AutoField(primary_key=True)
    employee_id = models.IntegerField()
    position_id = models.IntegerField()
    date_start = models.DateField()
    date_end = models.DateField()

    class Meta:
        db_table = 'Positions_History'

class Groups(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=255)
    class_id = models.IntegerField()

    class Meta:
        db_table = 'Groups'

class Classes(models.Model):
    id = models.AutoField(primary_key=True)
    type = models.CharField(max_length=255)
    date = models.DateField()
    trainer_id = models.IntegerField()
    group_id = models.IntegerField()
    stable_id = models.IntegerField()

    class Meta:
        db_table = 'Classes'

class Stables(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=255)
    address_id = models.IntegerField()

    class Meta:
        db_table = 'Stables'

class Horses(models.Model):
    id = models.AutoField(primary_key=True)
    breed = models.CharField(max_length=255)
    height = models.IntegerField()
    color = models.CharField(max_length=255)
    eye_color = models.CharField(max_length=255)
    age = models.IntegerField()
    origin = models.CharField(max_length=255)
    hairstyle_id = models.IntegerField()
    accessories_id = models.IntegerField()

    class Meta:
        db_table = 'Horses'

class Tournaments(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=255)
    date = models.DateField()
    address_id = models.IntegerField()

    class Meta:
        db_table = 'Tournaments'

class Tournament_Participants(models.Model):
    id = models.AutoField(primary_key=True)
    tournament_id = models.IntegerField()
    contestant_id = models.IntegerField()

    class Meta:
        db_table = 'Tournament_Participants'
