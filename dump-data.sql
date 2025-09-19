use DigitalAssetManagement;

-- Sample data for tenant
INSERT INTO tenant (id, name, created_at, updated_at) VALUES
                                                          (NEWID(), 'Tenant A', GETDATE(), GETDATE()),
                                                          (NEWID(), 'Tenant B', GETDATE(), GETDATE());

-- Sample data for space
INSERT INTO space (tenant_id, id, name, created_at, updated_at) VALUES
                                                                    ((SELECT id FROM tenant WHERE name = 'Tenant A'), NEWID(), 'Space 1', GETDATE(), GETDATE()),
                                                                    ((SELECT id FROM tenant WHERE name = 'Tenant A'), NEWID(), 'Space 2', GETDATE(), GETDATE()),
                                                                    ((SELECT id FROM tenant WHERE name = 'Tenant B'), NEWID(), 'Space 3', GETDATE(), GETDATE());

-- Sample data for folder
INSERT INTO folder (tenant_id, id, space_id, parent_id, name, created_at, updated_at, is_deleted) VALUES
                                                                                                      ((SELECT id FROM tenant WHERE name = 'Tenant A'), NEWID(), (SELECT id FROM space WHERE name = 'Space 1'), NULL, 'Folder 1', GETDATE(), GETDATE(), 0),
                                                                                                      ((SELECT id FROM tenant WHERE name = 'Tenant A'), NEWID(), (SELECT id FROM space WHERE name = 'Space 1'), NULL, 'Folder 2', GETDATE(), GETDATE(), 0),
                                                                                                      ((SELECT id FROM tenant WHERE name = 'Tenant B'), NEWID(), (SELECT id FROM space WHERE name = 'Space 3'), NULL, 'Folder 3', GETDATE(), GETDATE(), 0);

-- Sample data for asset
INSERT INTO asset (tenant_id, id, space_id, folder_id, display_name, file_path, thumbnail_path, metadata, created_at, updated_at, is_deleted) VALUES
                                                                                                                                                  ((SELECT id FROM tenant WHERE name = 'Tenant A'), NEWID(), (SELECT id FROM space WHERE name = 'Space 1'), (SELECT id FROM folder WHERE name = 'Folder 1'), 'Asset 1', '/path/to/file1', '/path/to/thumb1', '{"key": "value"}', GETDATE(), GETDATE(), 0),
                                                                                                                                                  ((SELECT id FROM tenant WHERE name = 'Tenant A'), NEWID(), (SELECT id FROM space WHERE name = 'Space 1'), (SELECT id FROM folder WHERE name = 'Folder 2'), 'Asset 2', '/path/to/file2', '/path/to/thumb2', '{"key": "value"}', GETDATE(), GETDATE(), 0),
                                                                                                                                                  ((SELECT id FROM tenant WHERE name = 'Tenant B'), NEWID(), (SELECT id FROM space WHERE name = 'Space 3'), NULL, 'Asset 3', '/path/to/file3', '/path/to/thumb3', '{"key": "value"}', GETDATE(), GETDATE(), 0);


-- Sample data for role
INSERT INTO role (tenant_id, id, name) VALUES
                                           ((SELECT id FROM tenant WHERE name = 'Tenant A'), NEWID(), 'Admin'),
                                           ((SELECT id FROM tenant WHERE name = 'Tenant A'), NEWID(), 'User'),
                                           ((SELECT id FROM tenant WHERE name = 'Tenant B'), NEWID(), 'Admin');

-- Sample data for user
INSERT INTO user_info (tenant_id, id, role_id, username, email, password, created_at, updated_at, is_activated) VALUES
                                                                                                                    ((SELECT id FROM tenant WHERE name = 'Tenant A'), NEWID(), (SELECT id FROM role WHERE name = 'Admin' and tenant_id = (SELECT id FROM tenant WHERE name = 'Tenant A')), 'user1', 'user1@example.com', 'hash1', GETDATE(), GETDATE(), 1),
                                                                                                                    ((SELECT id FROM tenant WHERE name = 'Tenant A'), NEWID(), (SELECT id FROM role  WHERE name = 'User' and tenant_id = (SELECT id FROM tenant WHERE name = 'Tenant A')), 'user2', 'user2@example.com', 'hash2', GETDATE(), GETDATE(), 1),
                                                                                                                    ((SELECT id FROM tenant WHERE name = 'Tenant B'), NEWID(), (SELECT id FROM role  WHERE name = 'Admin' and tenant_id = (SELECT id FROM tenant WHERE name = 'Tenant B')), 'user3', 'user3@example.com', 'hash3', GETDATE(), GETDATE(), 1);


-- Sample data for permission
INSERT INTO permission (tenant_id, id, name) VALUES
                                                 ((SELECT id FROM tenant WHERE name = 'Tenant A'), NEWID(), 'Read'),
                                                 ((SELECT id FROM tenant WHERE name = 'Tenant A'), NEWID(), 'Write'),
                                                 ((SELECT id FROM tenant WHERE name = 'Tenant B'), NEWID(), 'Read');

-- Sample data for role_permission
INSERT INTO role_permission (id, tenant_id, role_id, permission_id) VALUES
                                                                        (NEWID(),(SELECT id FROM tenant WHERE name = 'Tenant A'), (SELECT id FROM role WHERE name = 'Admin' and tenant_id = (SELECT id FROM tenant WHERE name = 'Tenant A')), (SELECT id FROM permission WHERE name = 'Write')),
                                                                        (NEWID(),(SELECT id FROM tenant WHERE name = 'Tenant A'), (SELECT id FROM role WHERE name = 'User'), (SELECT id FROM space WHERE name = 'Read')),
                                                                        (NEWID(),(SELECT id FROM tenant WHERE name = 'Tenant B'), (SELECT id FROM role WHERE name = 'Admin' and tenant_id = (SELECT id FROM tenant WHERE name = 'Tenant ')), (SELECT id FROM space WHERE name = 'Write'));

-- Sample data for user_space
INSERT INTO user_space (id, tenant_id, user_id, space_id, role_id, joined_at) VALUES
                                                                                  (NEWID(),(SELECT id FROM tenant WHERE name = 'Tenant A'), (SELECT id FROM user_info WHERE username = 'user1'), (SELECT id FROM space WHERE name = 'Space 1'), (SELECT id FROM role WHERE name = 'Admin' and tenant_id = (SELECT id FROM tenant WHERE name = 'Tenant A')), GETDATE()),
                                                                                  (NEWID(),(SELECT id FROM tenant WHERE name = 'Tenant A'), (SELECT id FROM user_info WHERE username = 'user2'), (SELECT id FROM space WHERE name = 'Space 2'), (SELECT id FROM role WHERE name = 'User'), GETDATE()),
                                                                                  (NEWID(),(SELECT id FROM tenant WHERE name = 'Tenant B'), (SELECT id FROM user_info WHERE username = 'user3'), (SELECT id FROM space WHERE name = 'Space 3'), (SELECT id FROM role WHERE name = 'Admin' and tenant_id = (SELECT id FROM tenant WHERE name = 'Tenant B')), GETDATE());

-- Sample data for credential
INSERT INTO credential (tenant_id, id, user_id, api_key, secret_key, created_at, updated_at, status) VALUES
                                                                                                         ((SELECT id FROM tenant WHERE name = 'Tenant A'), NEWID(), (SELECT id FROM user_info WHERE username = 'user1'), 'api_key_1', 'secret_key_1', GETDATE(), GETDATE(), 'active'),
                                                                                                         ((SELECT id FROM tenant WHERE name = 'Tenant A'), NEWID(), (SELECT id FROM user_info WHERE username = 'user2'), 'api_key_2', 'secret_key_2', GETDATE(), GETDATE(), 'inactive'),
                                                                                                         ((SELECT id FROM tenant WHERE name = 'Tenant B'), NEWID(), (SELECT id FROM user_info WHERE username = 'user3'), 'api_key_3', 'secret_key_3', GETDATE(), GETDATE(), 'active');

-- Sample data for user_folder
INSERT INTO user_folder (id, tenant_id, user_id, folder_id, role_id) VALUES
                                                                         (NEWID(),(SELECT id FROM tenant WHERE name = 'Tenant A'), (SELECT id FROM user_info WHERE username = 'user1'), (SELECT id FROM folder WHERE name = 'Folder 1'),  (SELECT id FROM role WHERE name = 'Admin' and tenant_id = (SELECT id FROM tenant WHERE name = 'Tenant A'))),
                                                                         (NEWID(),(SELECT id FROM tenant WHERE name = 'Tenant A'), (SELECT id FROM user_info WHERE username = 'user2'), (SELECT id FROM folder WHERE name = 'Folder 2'), (SELECT id FROM role WHERE name = 'User')),
                                                                         (NEWID(),(SELECT id FROM tenant WHERE name = 'Tenant B'), (SELECT id FROM user_info WHERE username = 'user3'), (SELECT id FROM folder WHERE name = 'Folder 3'),  (SELECT id FROM role WHERE name = 'Admin' and tenant_id = (SELECT id FROM tenant WHERE name = 'Tenant B')));

-- Sample data for invite
INSERT INTO invite (tenant_id, id, invited_by_id, invitee_email, role_id, space_id, status, invited_at, response_at) VALUES
                                                                                                                         ((SELECT id FROM tenant WHERE name = 'Tenant A'), NEWID(), (SELECT id FROM user_info WHERE username = 'user1'), 'invitee1@example.com', (SELECT id FROM role WHERE name = 'Admin' and tenant_id = (SELECT id FROM tenant WHERE name = 'Tenant A')), (SELECT id FROM space WHERE name = 'Space 1'), 'pending', GETDATE(), NULL),
                                                                                                                         ((SELECT id FROM tenant WHERE name = 'Tenant A'), NEWID(), (SELECT id FROM user_info WHERE username = 'user2'), 'invitee2@example.com', (SELECT id FROM role WHERE name = 'User'), (SELECT id FROM space WHERE name = 'Space 2'), 'accepted', GETDATE(), GETDATE()),
                                                                                                                         ((SELECT id FROM tenant WHERE name = 'Tenant B'), NEWID(), (SELECT id FROM user_info WHERE username = 'user3'), 'invitee3@example.com', (SELECT id FROM role WHERE name = 'Admin' and tenant_id = (SELECT id FROM tenant WHERE name = 'Tenant B')), (SELECT id FROM space WHERE name = 'Space 3'), 'rejected', GETDATE(), GETDATE());
