use DigitalAssetManagement;

-- Insert Users
INSERT INTO user_info (id, username, email, password, created_at, updated_at) VALUES
(NEWID(), 'userA', 'userA@example.com', 'passwordA', GETDATE(), GETDATE()),
(NEWID(), 'userB', 'userB@example.com', 'passwordB', GETDATE(), GETDATE());

-- Insert Tenants
INSERT INTO tenant (id, name, owned_by_id, created_at, updated_at) VALUES
(NEWID(), 'TenantA', (SELECT id FROM user_info WHERE username = 'userA'), GETDATE(), GETDATE()),
(NEWID(), 'TenantB', (SELECT id FROM user_info WHERE username = 'userA'), GETDATE(), GETDATE());



-- Insert Roles
INSERT INTO role (id, name, value) VALUES
(NEWID(), 'Admin', 1),
(NEWID(), 'User', 2);

-- Insert Spaces
INSERT INTO space (id, tenant_id, name, created_at, updated_at) VALUES
(NEWID(), (SELECT id FROM tenant WHERE name = 'TenantA'), 'SpaceA', GETDATE(), GETDATE()),
(NEWID(), (SELECT id FROM tenant WHERE name = 'TenantB'), 'SpaceB', GETDATE(), GETDATE());

-- Insert Folders
INSERT INTO folder (id, space_id, parent_id, name, created_at, updated_at) VALUES
(NEWID(), (SELECT id FROM space WHERE name = 'SpaceA'), NULL, 'FolderA', GETDATE(), GETDATE()),
(NEWID(), (SELECT id FROM space WHERE name = 'SpaceB'), (SELECT id FROM folder WHERE name = 'FolderA'), 'FolderB', GETDATE(), GETDATE());

-- Insert Assets
INSERT INTO asset (id, space_id, folder_id, name, file_path, metadata, created_at, updated_at) VALUES
(NEWID(), (SELECT id FROM space WHERE name = 'SpaceA'), (SELECT id FROM folder WHERE name = 'FolderA'), 'AssetA', '/path/to/assetA.jpg', 'MetadataA', GETDATE(), GETDATE()),
(NEWID(), (SELECT id FROM space WHERE name = 'SpaceB'), (SELECT id FROM folder WHERE name = 'FolderB'), 'AssetB', '/path/to/assetB.jpg', 'MetadataB', GETDATE(), GETDATE());

-- Insert Credentials
INSERT INTO credential (id, user_id, api_key, secret_key, created_at, updated_at, status) VALUES
(NEWID(), (SELECT id FROM user_info WHERE username = 'userA'), 'apiKeyA', 'secretKeyA', GETDATE(), GETDATE(), 'ACTIVE'),
(NEWID(), (SELECT id FROM user_info WHERE username = 'userB'), 'apiKeyB', 'secretKeyB', GETDATE(), GETDATE(), 'INACTIVE');

-- Insert UserFolders
INSERT INTO user_folder (id, user_id, folder_id, access_level) VALUES
(NEWID(), (SELECT id FROM user_info WHERE username = 'userA'), (SELECT id FROM folder WHERE name = 'FolderA'), 'ADMIN'),
(NEWID(), (SELECT id FROM user_info WHERE username = 'userB'), (SELECT id FROM folder WHERE name = 'FolderB'), 'READ');

-- Insert UserSpaces
INSERT INTO user_space (id, user_id, space_id, role_id, joined_at) VALUES
(NEWID(), (SELECT id FROM user_info WHERE username = 'userA'), (SELECT id FROM space WHERE name = 'SpaceA'), (SELECT id FROM role WHERE name = 'Admin'), GETDATE()),
(NEWID(), (SELECT id FROM user_info WHERE username = 'userB'), (SELECT id FROM space WHERE name = 'SpaceB'), (SELECT id FROM role WHERE name = 'User'), GETDATE());

