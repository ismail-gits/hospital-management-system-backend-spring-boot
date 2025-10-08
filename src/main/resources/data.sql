INSERT INTO patient (name, email, gender, birth_date, blood_group, created_at)
VALUES
    ('Mohammed Ismail', 'ismail@gmail.com', 'MALE', '2001-03-06', 'O_POSITIVE', CURRENT_TIMESTAMP),
    ('Faisal Khan', 'faisal@gmail.com', 'MALE', '1999-12-30', 'A_POSITIVE', CURRENT_TIMESTAMP),
    ('Mohammed Nabil Siddique', 'nabil@gmail.com', 'MALE', '2002-10-19', 'B_POSITIVE', CURRENT_TIMESTAMP),
    ('Sara Suheera', 'sara@gmail.com', 'FEMALE', '2002-04-23', 'AB_NEGATIVE', CURRENT_TIMESTAMP),
    ('Rida Khan', 'rida@gmail.com', 'FEMALE', '2007-01-12', 'B_NEGATIVE', CURRENT_TIMESTAMP);

INSERT INTO doctor (name, email, specialization, created_at)
VALUES
    ('Dr. Mohammed Arsalaan', 'arsalaan@gmail.com', 'Cardiologist', CURRENT_TIMESTAMP),
    ('Dr. Rida Uzamin', 'rida@gmail.com', 'Dentist', CURRENT_TIMESTAMP),
    ('Dr. Siddique Khan', 'siddique@gmail.com', 'Ophthalmologist', CURRENT_TIMESTAMP);

INSERT INTO appointment (appointment_time, reason, status, doctor_id, patient_id)
VALUES
    ('2025-09-30 10:30:00', 'General Checkup', 'PENDING', 1, 3),
    ('2025-10-15 15:12:23', 'Skin Rashes', 'COMPLETED', 2, 2),
    ('2025-09-30 23:30:12', 'Knee Pain', 'PENDING', 3, 3),
    ('2025-12-12 13:30:00', 'Consultations', 'PROCESSING', 1, 1),
    ('2025-03-12 12:40:00', 'Follow-up visit', 'COMPLETED', 1, 4),
    ('2025-03-13 13:40:00', 'Allergy Treatment', 'PENDING', 2, 5);