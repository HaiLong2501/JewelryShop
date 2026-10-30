-- Facebook login: link a user account to a Facebook user id
-- Filtered indexes require these options (on by default in SSMS, off in sqlcmd)
SET ANSI_NULLS ON;
SET QUOTED_IDENTIFIER ON;
GO
ALTER TABLE dbo.tblUsers ADD facebookID NVARCHAR(64) NULL;
GO
CREATE UNIQUE INDEX UX_tblUsers_facebookID ON dbo.tblUsers(facebookID) WHERE facebookID IS NOT NULL;
GO
