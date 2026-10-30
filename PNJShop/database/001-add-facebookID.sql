-- Facebook login: link a user account to a Facebook user id
ALTER TABLE dbo.tblUsers ADD facebookID NVARCHAR(64) NULL;
GO
CREATE UNIQUE INDEX UX_tblUsers_facebookID ON dbo.tblUsers(facebookID) WHERE facebookID IS NOT NULL;
GO
