-- Mailing List Setting
delete from mailing_lists;
delete from mailing_list_membership;

alter sequence mailing_list_sequence restart with 1;
alter sequence mailing_list_membership_sequence restart with 1;

-- Generating Mailing Test data for Albania, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('6a63dfd3-7dd7-4eb6-a209-c57447a56393', 'true', 'albania@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('7b603907-0730-4d2d-9e97-0e1fe94f1ade', 'false', 'albania@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('7b603907-0730-4d2d-9e97-0e1fe94f1ade', 'true', 'albania.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Argentina, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('902cf65a-4220-4428-8167-d6695c9b84d9', 'true', 'argentina@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('125041b8-d3f5-48ba-84e6-c3cc0e83a86a', 'false', 'argentina@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('125041b8-d3f5-48ba-84e6-c3cc0e83a86a', 'true', 'argentina.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Australia, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('1f6fc75f-f8af-433c-bf9e-bfecb6970239', 'true', 'australia@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('dc2eff97-c6ef-4915-b2c8-9bde039c62ca', 'false', 'australia@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('dc2eff97-c6ef-4915-b2c8-9bde039c62ca', 'true', 'australia.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Austria, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('da799505-cbf4-4bb8-bbcc-7e7705a7892f', 'true', 'austria@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('9f2c4db6-38c9-4a2f-bdaf-141bd1eb4c13', 'false', 'austria@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('9f2c4db6-38c9-4a2f-bdaf-141bd1eb4c13', 'true', 'austria.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Azerbaijan, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('c64483d8-2f73-48bc-8030-676e28e83323', 'true', 'azerbaijan@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('df404581-d167-4c93-8d3f-9c71e71a8e92', 'false', 'azerbaijan@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('df404581-d167-4c93-8d3f-9c71e71a8e92', 'true', 'azerbaijan.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Bangladesh.Aviation, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('44c9edd1-86d1-495a-876b-95b8c6c322c8', 'true', 'bangladesh.aviation@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('f2de0d7a-e502-49ea-afe1-308884d5f22f', 'false', 'bangladesh.aviation@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('f2de0d7a-e502-49ea-afe1-308884d5f22f', 'true', 'bangladesh.aviation.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Belarus, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('fea5b4ff-58a5-4d9b-a981-7f9520d9f488', 'true', 'belarus@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('da122a82-b195-4746-9652-5d9323f4a1f6', 'false', 'belarus@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('da122a82-b195-4746-9652-5d9323f4a1f6', 'true', 'belarus.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Belgium, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('e375c184-dd9b-422d-ad26-7e03a3ba7dca', 'true', 'belgium@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('02b5ddc0-541f-47eb-ae77-6d3b9a33f2a9', 'false', 'belgium@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('02b5ddc0-541f-47eb-ae77-6d3b9a33f2a9', 'true', 'belgium.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Bolivia, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('a38b52a0-d05b-4bbb-a794-1ac5a17a04c1', 'true', 'bolivia@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('cac42b77-f69a-4e0e-966f-588a4960d231', 'false', 'bolivia@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('cac42b77-f69a-4e0e-966f-588a4960d231', 'true', 'bolivia.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for BosniaandHerzegovina, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('b11dea53-4350-4353-8008-27052e276917', 'true', 'bosniaandherzegovina@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('9da0c7b2-f3fa-40c8-89e9-126bb677a514', 'false', 'bosniaandherzegovina@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('9da0c7b2-f3fa-40c8-89e9-126bb677a514', 'true', 'bosniaandherzegovina.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Brazil, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('1dd2f402-7fbc-4ca2-9e46-036edd95b914', 'true', 'brazil@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('54523d72-a048-4e7b-8a49-d615377fc0f7', 'false', 'brazil@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('54523d72-a048-4e7b-8a49-d615377fc0f7', 'true', 'brazil.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Canada, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('9fcebe71-d3fa-426e-b5ed-97d0cd4d4582', 'true', 'canada@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('be543ebb-5052-4748-9542-4908f5769d25', 'false', 'canada@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('be543ebb-5052-4748-9542-4908f5769d25', 'true', 'canada.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Chile, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('47d78e60-4370-4533-b936-152fc29698c5', 'true', 'chile@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('d28f856d-8b8c-4288-a193-a7dd4a2490a9', 'false', 'chile@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('d28f856d-8b8c-4288-a193-a7dd4a2490a9', 'true', 'chile.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for China, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('98f3aed9-4246-4bfe-bf34-a88c173c7370', 'true', 'china@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('92219b23-3bc2-4593-928c-92aee1d8dc44', 'false', 'china@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('92219b23-3bc2-4593-928c-92aee1d8dc44', 'true', 'china.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Colombia, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('e4012e2b-b444-4ead-9223-4efabbeaac68', 'true', 'colombia@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('34823d36-9b1d-4058-88f2-deaeb95f11ab', 'false', 'colombia@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('34823d36-9b1d-4058-88f2-deaeb95f11ab', 'true', 'colombia.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Croatia, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('aa681bc9-0707-4e3c-9cd1-d33c4af13f00', 'true', 'croatia@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('a47fd465-bb39-4c8c-8a9e-d51671790567', 'false', 'croatia@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('a47fd465-bb39-4c8c-8a9e-d51671790567', 'true', 'croatia.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Cyprus, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('56dd0070-e1f3-403e-bf58-87f4b6b5b53e', 'true', 'cyprus@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('ff8b0c56-dfdc-43cf-9fb9-a000cbd14c56', 'false', 'cyprus@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('ff8b0c56-dfdc-43cf-9fb9-a000cbd14c56', 'true', 'cyprus.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for CzechRepublic, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('04beae66-f474-4942-8daf-e22c3b2a9bd8', 'true', 'czechrepublic@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('78206b95-d772-41fb-89fb-6e9993b05f80', 'false', 'czechrepublic@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('78206b95-d772-41fb-89fb-6e9993b05f80', 'true', 'czechrepublic.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Denmark, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('23fdd892-7cc1-49f1-b8e5-dee37f16e74d', 'true', 'denmark@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('82c0bf70-80d3-4fbb-9832-1c772ca300c5', 'false', 'denmark@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('82c0bf70-80d3-4fbb-9832-1c772ca300c5', 'true', 'denmark.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Ecuador, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('c723609d-4623-459f-bd8c-7486a49b2364', 'true', 'ecuador@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('06c51fe0-8d43-4a06-aa96-faaff3cdcce6', 'false', 'ecuador@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('06c51fe0-8d43-4a06-aa96-faaff3cdcce6', 'true', 'ecuador.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Egypt, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('ec08a712-f75b-40a7-a74d-388230f7ee0c', 'true', 'egypt@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('a37b639f-b2e9-4100-b447-59ee217ebf12', 'false', 'egypt@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('a37b639f-b2e9-4100-b447-59ee217ebf12', 'true', 'egypt.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Estonia, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('eb7f2270-a777-4e82-bb49-f8d67a487594', 'true', 'estonia@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('2007aabf-b156-42bc-afcc-c2086d6f879f', 'false', 'estonia@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('2007aabf-b156-42bc-afcc-c2086d6f879f', 'true', 'estonia.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Finland, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('de8546e4-67dd-4329-9aed-0391c4390e5f', 'true', 'finland@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('544d1a86-9163-4409-aabd-54f1096100e3', 'false', 'finland@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('544d1a86-9163-4409-aabd-54f1096100e3', 'true', 'finland.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for France, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('714c6061-4df8-469f-9998-87f17620322c', 'true', 'france@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('9f26d467-f35f-44ba-8c2e-510ba05d9e11', 'false', 'france@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('9f26d467-f35f-44ba-8c2e-510ba05d9e11', 'true', 'france.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Gambia, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('4e1503a5-45a0-421c-8c92-c0636fe849e8', 'true', 'gambia@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('089727fe-55c5-45f5-a7f9-729f9df801da', 'false', 'gambia@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('089727fe-55c5-45f5-a7f9-729f9df801da', 'true', 'gambia.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Germany, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('8771e306-0e5e-4eb8-bad1-7f7837b97762', 'true', 'germany@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('9d10455b-834a-4570-89ca-c7ce4c68eb3a', 'false', 'germany@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('9d10455b-834a-4570-89ca-c7ce4c68eb3a', 'true', 'germany.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Ghana, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('28735bcd-ac28-4ad7-86da-34e342a225c3', 'true', 'ghana@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('78c15605-f41d-4c0f-9694-140b65af9e61', 'false', 'ghana@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('78c15605-f41d-4c0f-9694-140b65af9e61', 'true', 'ghana.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Greece, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('f4455e0c-5b8e-4dfb-ad3b-ffc224f3824b', 'true', 'greece@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('afc2175c-740f-4d8f-9118-70a4b7ead7c0', 'false', 'greece@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('afc2175c-740f-4d8f-9118-70a4b7ead7c0', 'true', 'greece.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for HongKong, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('33864a9b-5032-475a-8bc5-3c43336a409e', 'true', 'hongkong@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('341ac3a6-ffde-42ec-83b1-a04227ec194b', 'false', 'hongkong@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('341ac3a6-ffde-42ec-83b1-a04227ec194b', 'true', 'hongkong.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Hungary, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('0e12c5c0-9ecc-4d35-90ef-e2d6a4cdcc6a', 'true', 'hungary@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('4f6591a4-c5fe-4885-ab9d-46cbc44bc969', 'false', 'hungary@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('4f6591a4-c5fe-4885-ab9d-46cbc44bc969', 'true', 'hungary.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Iceland, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('d4352cea-a7ba-4254-82cf-f73e91a8e965', 'true', 'iceland@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('872ead93-530b-4bf2-854f-f539077dbd1a', 'false', 'iceland@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('872ead93-530b-4bf2-854f-f539077dbd1a', 'true', 'iceland.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for India.KU, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('b9f73e60-b07a-488b-bf5c-ef932d4d0658', 'true', 'india.ku@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('35c5df32-4543-4813-ae8e-631fd46b5b99', 'false', 'india.ku@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('35c5df32-4543-4813-ae8e-631fd46b5b99', 'true', 'india.ku.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Iran, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('12ff37b6-5ac9-4c96-8949-5dc858fd4870', 'true', 'iran@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('e5463cb8-f6ad-49d5-83cd-2d836f70c57f', 'false', 'iran@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('e5463cb8-f6ad-49d5-83cd-2d836f70c57f', 'true', 'iran.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Ireland, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('e9372753-1931-4342-bb8a-7807b64db7f0', 'true', 'ireland@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('4a209296-fabe-463b-b8c0-16dbcb7341b6', 'false', 'ireland@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('4a209296-fabe-463b-b8c0-16dbcb7341b6', 'true', 'ireland.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Israel, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('e5b44bdb-170d-4fa6-aa84-6dc06f00fc6a', 'true', 'israel@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('9c564ca0-6eda-47cf-be3d-3fa6d7918142', 'false', 'israel@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('9c564ca0-6eda-47cf-be3d-3fa6d7918142', 'true', 'israel.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Italy, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('d188373c-5692-4849-8193-51c14303245d', 'true', 'italy@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('f75c041f-e9b4-4d45-b0d7-578e29e6c54c', 'false', 'italy@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('f75c041f-e9b4-4d45-b0d7-578e29e6c54c', 'true', 'italy.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Jamaica, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('cf447972-b50b-4d33-9010-cd9f0cad8f6b', 'true', 'jamaica@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('4d94ad57-0d19-4428-a9cc-7452a2d129eb', 'false', 'jamaica@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('4d94ad57-0d19-4428-a9cc-7452a2d129eb', 'true', 'jamaica.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Japan, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('0c40dce4-093e-4581-98f0-b8695e5a7c82', 'true', 'japan@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('6ef0a009-b279-4f20-87c8-a218488bca7f', 'false', 'japan@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('6ef0a009-b279-4f20-87c8-a218488bca7f', 'true', 'japan.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Jordan, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('a8ba86f9-8129-4466-b9e8-6e9dd3b21a22', 'true', 'jordan@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('c24cdcf3-eeee-4bac-9107-b0570fe96fa0', 'false', 'jordan@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('c24cdcf3-eeee-4bac-9107-b0570fe96fa0', 'true', 'jordan.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Kazakhstan, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('f1556862-958e-4740-8ae6-50fe35476e66', 'true', 'kazakhstan@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('99174b65-d9fa-42dd-a01c-4c37b832a916', 'false', 'kazakhstan@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('99174b65-d9fa-42dd-a01c-4c37b832a916', 'true', 'kazakhstan.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Kenya, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('505d8241-4f71-461d-b9cd-db4c90f5b844', 'true', 'kenya@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('5f489c47-18f4-441f-a355-add05547876a', 'false', 'kenya@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('5f489c47-18f4-441f-a355-add05547876a', 'true', 'kenya.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Korea, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('08676d20-4308-42df-ba67-f27927182d72', 'true', 'korea@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('df4cdec0-b8e9-4bc3-9b8d-87f6672a9cf8', 'false', 'korea@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('df4cdec0-b8e9-4bc3-9b8d-87f6672a9cf8', 'true', 'korea.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Latvia, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('a657b0ce-b120-4bcf-824c-ac9739ebfb37', 'true', 'latvia@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('377ab884-b042-4ffd-9041-5455bc79baf4', 'false', 'latvia@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('377ab884-b042-4ffd-9041-5455bc79baf4', 'true', 'latvia.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Lebanon, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('40fdc473-da7a-4a14-a60b-f40915b6936b', 'true', 'lebanon@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('baee5c49-a95d-4811-90c3-a3d7f7069b61', 'false', 'lebanon@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('baee5c49-a95d-4811-90c3-a3d7f7069b61', 'true', 'lebanon.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Liberia, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('594cf553-196a-4b02-a50e-c35c6152a5ac', 'true', 'liberia@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('c8aa8dd7-fad1-48ba-af26-afe1ad51ce3e', 'false', 'liberia@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('c8aa8dd7-fad1-48ba-af26-afe1ad51ce3e', 'true', 'liberia.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Lithuania, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('9af8fe53-19c6-495c-a8c3-3b1c6dec2406', 'true', 'lithuania@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('8e25de4a-da72-42c7-a035-814f85424138', 'false', 'lithuania@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('8e25de4a-da72-42c7-a035-814f85424138', 'true', 'lithuania.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Macao, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('64c1e3e4-3f67-4890-adff-8b62e9e45448', 'true', 'macao@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('04abfe93-4720-4b24-b1e6-432489dc0819', 'false', 'macao@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('04abfe93-4720-4b24-b1e6-432489dc0819', 'true', 'macao.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Macedonia, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('6ce74341-8f4f-4b55-8ff6-d50542217f9a', 'true', 'macedonia@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('ec479694-21ed-4c24-a8a8-ef8b0eb07bd6', 'false', 'macedonia@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('ec479694-21ed-4c24-a8a8-ef8b0eb07bd6', 'true', 'macedonia.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Malaysia, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('7eae41cc-03d0-48e1-ab01-2b9ad48d7f2f', 'true', 'malaysia@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('1494f627-3fba-4b3a-8cf0-dde608d5f60c', 'false', 'malaysia@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('1494f627-3fba-4b3a-8cf0-dde608d5f60c', 'true', 'malaysia.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Malta, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('f362e01c-aa38-4e37-b221-45ddb8bdf968', 'true', 'malta@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('c75de18f-c9f0-41ed-bee3-3fe2f1826deb', 'false', 'malta@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('c75de18f-c9f0-41ed-bee3-3fe2f1826deb', 'true', 'malta.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Mexico, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('1e7336f2-8513-4de2-9f3b-6b73a7da5fcb', 'true', 'mexico@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('b18a734e-44fa-4a0b-8cff-3dc331d6893b', 'false', 'mexico@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('b18a734e-44fa-4a0b-8cff-3dc331d6893b', 'true', 'mexico.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Mongolia, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('f68a2678-38fe-4d69-89f9-07e01f537ef0', 'true', 'mongolia@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('0ac91424-ca7e-4be4-87d7-2f35ca665cde', 'false', 'mongolia@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('0ac91424-ca7e-4be4-87d7-2f35ca665cde', 'true', 'mongolia.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Montenegro, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('9f6adbcf-e81a-44a7-85ad-812da9796e69', 'true', 'montenegro@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('3390a51e-311a-4c62-a61f-00fc4820e666', 'false', 'montenegro@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('3390a51e-311a-4c62-a61f-00fc4820e666', 'true', 'montenegro.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Netherlands, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('161e692f-7a38-4fe5-b8c4-c58c5573b17b', 'true', 'netherlands@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('ef8762cc-3378-4dcf-b686-0baa60a4525f', 'false', 'netherlands@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('ef8762cc-3378-4dcf-b686-0baa60a4525f', 'true', 'netherlands.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Nigeria, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('1a2b1987-e955-493c-8632-9a2fdb5d44b8', 'true', 'nigeria@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('24fd88db-8527-4c3c-9c2c-8e46a63df310', 'false', 'nigeria@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('24fd88db-8527-4c3c-9c2c-8e46a63df310', 'true', 'nigeria.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Norway, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('d975e033-7fc0-4980-b1e1-9ae97eb43554', 'true', 'norway@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('a9bbc993-885a-4c48-b3be-50a89d691f59', 'false', 'norway@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('a9bbc993-885a-4c48-b3be-50a89d691f59', 'true', 'norway.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Oman, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('c9c2c6e8-6fbd-43ae-9bd5-cdb9c9df2068', 'true', 'oman@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('c22dd9c4-07fe-4e0a-bc5c-7120fc1c4e4d', 'false', 'oman@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('c22dd9c4-07fe-4e0a-bc5c-7120fc1c4e4d', 'true', 'oman.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Pakistan, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('49e7dff5-e152-4da9-bc7f-e07344473eba', 'true', 'pakistan@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('97027062-a067-4146-b5ed-858ed9d03d7d', 'false', 'pakistan@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('97027062-a067-4146-b5ed-858ed9d03d7d', 'true', 'pakistan.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Panama, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('e8922884-8d42-4541-9f08-340dd1a6b0d1', 'true', 'panama@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('abd8b394-1fe3-4ccf-8e15-64bf4a36d8b1', 'false', 'panama@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('abd8b394-1fe3-4ccf-8e15-64bf4a36d8b1', 'true', 'panama.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Peru, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('f6269393-9df7-44ad-8ab3-6d8e8cf20a22', 'true', 'peru@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('f77f5093-31fa-40f7-b85f-9a540a8f5433', 'false', 'peru@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('f77f5093-31fa-40f7-b85f-9a540a8f5433', 'true', 'peru.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Philippines, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('a2d98692-74d2-44de-8c38-557c67721374', 'true', 'philippines@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('a230e78f-4b74-463d-b6ad-8020166c8b6a', 'false', 'philippines@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('a230e78f-4b74-463d-b6ad-8020166c8b6a', 'true', 'philippines.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Poland, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('70e293c2-7142-4db2-91a7-cc0bf8fe5f65', 'true', 'poland@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('dcdf3f10-3a51-4970-933e-93b01a70b0b1', 'false', 'poland@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('dcdf3f10-3a51-4970-933e-93b01a70b0b1', 'true', 'poland.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Portugal, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('03fc1e57-0913-4c24-b250-5be5b64ddfad', 'true', 'portugal@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('25d9165f-1863-4327-81f2-8d12fcf97b6e', 'false', 'portugal@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('25d9165f-1863-4327-81f2-8d12fcf97b6e', 'true', 'portugal.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Qatar, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('36bcc1ad-cf1a-44dc-98ee-320b2c59f4f9', 'true', 'qatar@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('4c2a0116-14ac-4d22-8d5c-c3f3e332e4ce', 'false', 'qatar@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('4c2a0116-14ac-4d22-8d5c-c3f3e332e4ce', 'true', 'qatar.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Romania, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('c6248151-1b55-420d-a3cf-2d5b94b533e2', 'true', 'romania@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('fca658a0-db35-4037-b2f6-69bce461128a', 'false', 'romania@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('fca658a0-db35-4037-b2f6-69bce461128a', 'true', 'romania.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Russia, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('af16cc9f-0f91-4e0d-964d-62eb7b6e6977', 'true', 'russia@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('ea0b915f-a84e-4f74-85ba-80b5977963fc', 'false', 'russia@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('ea0b915f-a84e-4f74-85ba-80b5977963fc', 'true', 'russia.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Serbia, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('35404eb5-c46d-4edc-96d1-0f1a6f76fc21', 'true', 'serbia@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('09fc08e7-1541-4835-a81e-ddc5df88f9e6', 'false', 'serbia@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('09fc08e7-1541-4835-a81e-ddc5df88f9e6', 'true', 'serbia.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Slovakia, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('18767a92-94ba-4282-ab02-dc3287fd206e', 'true', 'slovakia@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('b5905239-db02-4be4-81ef-a2390aafb07a', 'false', 'slovakia@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('b5905239-db02-4be4-81ef-a2390aafb07a', 'true', 'slovakia.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Slovenia, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('ff5ec00d-5aad-40bf-a733-7cf3244ede4b', 'true', 'slovenia@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('dd61acdf-7a01-4123-8003-aef82310e772', 'false', 'slovenia@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('dd61acdf-7a01-4123-8003-aef82310e772', 'true', 'slovenia.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Spain, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('2aa253ae-c412-4041-8212-65978a14132a', 'true', 'spain@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('052c7719-33e2-4825-baf1-39bf7d6457c2', 'false', 'spain@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('052c7719-33e2-4825-baf1-39bf7d6457c2', 'true', 'spain.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for SriLanka, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('2e6551b5-c832-43a0-bb47-88af2e82fc79', 'true', 'srilanka@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('7ccf6069-6ada-4045-91c0-2ccdde794dc6', 'false', 'srilanka@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('7ccf6069-6ada-4045-91c0-2ccdde794dc6', 'true', 'srilanka.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Sweden, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('894c8eed-27dd-45d0-b00a-d6da51bdc8aa', 'true', 'sweden@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('df577354-cd95-4ab7-bce7-1756ecbb4b2e', 'false', 'sweden@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('df577354-cd95-4ab7-bce7-1756ecbb4b2e', 'true', 'sweden.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Switzerland, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('ddc46e01-c264-4e3c-9fb1-9914c0a3b0fe', 'true', 'switzerland@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('523cb4df-71d3-416d-80e9-192d338fede8', 'false', 'switzerland@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('523cb4df-71d3-416d-80e9-192d338fede8', 'true', 'switzerland.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Syria, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('c4990972-901c-44d2-be8e-042cead84491', 'true', 'syria@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('ddc44f18-401d-46a9-b60c-c42fa6db1958', 'false', 'syria@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('ddc44f18-401d-46a9-b60c-c42fa6db1958', 'true', 'syria.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Tajikistan, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('12551323-0ed7-44b0-8386-59f5e60553f2', 'true', 'tajikistan@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('5209399e-07a4-451e-92f6-6869ee3e201d', 'false', 'tajikistan@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('5209399e-07a4-451e-92f6-6869ee3e201d', 'true', 'tajikistan.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Tanzania, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('bd0e94e2-fc66-400e-996f-06de6eae8292', 'true', 'tanzania@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('22012cef-863f-45a2-adc1-168779ab587e', 'false', 'tanzania@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('22012cef-863f-45a2-adc1-168779ab587e', 'true', 'tanzania.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Thailand, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('f8f550a5-a3ba-4d7f-9263-1183098f7831', 'true', 'thailand@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('f0d692ac-b2f3-4c01-813b-e7636f5ca969', 'false', 'thailand@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('f0d692ac-b2f3-4c01-813b-e7636f5ca969', 'true', 'thailand.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Tunisia, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('59f04a7e-2e80-492b-b785-2f041580fd01', 'true', 'tunisia@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('24e523a7-aed2-4f13-ae6d-c1f1c01e9776', 'false', 'tunisia@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('24e523a7-aed2-4f13-ae6d-c1f1c01e9776', 'true', 'tunisia.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Turkey, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('bec6ba71-282c-4681-a46b-afbdbaf8d863', 'true', 'turkey@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('42732c68-9d2d-48e6-b06d-d8520f349df7', 'false', 'turkey@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('42732c68-9d2d-48e6-b06d-d8520f349df7', 'true', 'turkey.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Ukraine, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('eea96d05-6cd0-4bb7-8bed-3c4e7dadfed2', 'true', 'ukraine@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('3dd9a7a8-ad8c-47e4-b54f-c5a2759cfe84', 'false', 'ukraine@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('3dd9a7a8-ad8c-47e4-b54f-c5a2759cfe84', 'true', 'ukraine.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for UnitedArabEmirates, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('c218e924-f123-4144-b871-8bd1097659c4', 'true', 'unitedarabemirates@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('a4c71aee-09c0-401b-a547-d8ed4491e8e2', 'false', 'unitedarabemirates@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('a4c71aee-09c0-401b-a547-d8ed4491e8e2', 'true', 'unitedarabemirates.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for UnitedKingdom, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('389e6fbb-9e69-4987-aafe-2d9497798ee7', 'true', 'unitedkingdom@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('9bcf5332-cd41-44f0-b902-e853c291a436', 'false', 'unitedkingdom@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('9bcf5332-cd41-44f0-b902-e853c291a436', 'true', 'unitedkingdom.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for USA, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('0b403d38-e454-45f3-b2b4-876c4e220832', 'true', 'usa@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('eed907ec-bfa3-46c7-aadf-4bc313e93e80', 'false', 'usa@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('eed907ec-bfa3-46c7-aadf-4bc313e93e80', 'true', 'usa.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Uzbekistan, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('07e36800-de19-4c50-b1c4-d3e5c8e33ec7', 'true', 'uzbekistan@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('1b4a28d9-8ef3-42dc-86e1-b5043c3f1b10', 'false', 'uzbekistan@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('1b4a28d9-8ef3-42dc-86e1-b5043c3f1b10', 'true', 'uzbekistan.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for Vietnam, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('2fe23724-157e-4017-8946-0a3a2bef3fa8', 'true', 'vietnam@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('67fc0148-99c9-4991-bf0c-ca9239732e88', 'false', 'vietnam@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('67fc0148-99c9-4991-bf0c-ca9239732e88', 'true', 'vietnam.staff@dev4.iaeste.net', 'true');

-- Generating Mailing Test data for WestBank, move to Mailing SQL file
insert into mailing_lists (external_id, private, list_address, active) values ('063a150c-0ef6-4e9c-9b5a-f3d3d3e4e5c6', 'true', 'westbank@dev4.iaeste.net', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('16d990f9-8b82-4ca4-bb4c-a8cdb275fe4f', 'false', 'westbank@dev4.iaeste.org', 'true');
insert into mailing_lists (external_id, private, list_address, active) values ('16d990f9-8b82-4ca4-bb4c-a8cdb275fe4f', 'true', 'westbank.staff@dev4.iaeste.net', 'true');
