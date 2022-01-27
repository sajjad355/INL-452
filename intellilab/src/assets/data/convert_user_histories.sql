CREATE DEFINER=`root`@`localhost` PROCEDURE `convert_user_histories`()
BEGIN
	INSERT INTO somrusoft.il_user_history
		(activity,
		activity_time,
		component,
		description,
		username)
    SELECT
        REPLACE(action, '\n', '') as action,
        time,
        IFNULL(component, '') as component,
        IFNULL(description, '') as description,
        IFNULL(username, '') as username
	FROM somrusoft.userhistory;
END