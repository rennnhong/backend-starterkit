select
_user.id,
_user.account,
_user.user_name,
_user_role.*,
_role.id,
_role.name,
_role_permission.role_id,
_role_permission.permission_id,
_role_permission.action_id,
_permission.name,
_action.name
from  sys_user _user
left join sys_user_role _user_role on(_user.id = _user_role.users_id)
left join sys_role _role on(_role.id = _user_role.roles_id)
left join sys_role_permission _role_permission on(_role.id = _role_permission.role_id)
left join sys_permission _permission on(_permission.id = _role_permission.permission_id)
left join sys_action _action on(_action.id = _role_permission.action_id)