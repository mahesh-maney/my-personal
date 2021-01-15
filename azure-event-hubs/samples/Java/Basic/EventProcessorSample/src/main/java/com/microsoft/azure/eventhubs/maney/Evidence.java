package com.microsoft.azure.eventhubs.maney;

public class Evidence {
			private String role;
			private String roleAssignmentScope;
			private String roleAssignmentId;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRoleAssignmentScope() {
		return roleAssignmentScope;
	}

	public void setRoleAssignmentScope(String roleAssignmentScope) {
		this.roleAssignmentScope = roleAssignmentScope;
	}

	public String getRoleAssignmentId() {
		return roleAssignmentId;
	}

	public void setRoleAssignmentId(String roleAssignmentId) {
		this.roleAssignmentId = roleAssignmentId;
	}

	public String getRoleDefinitionId() {
		return roleDefinitionId;
	}

	public void setRoleDefinitionId(String roleDefinitionId) {
		this.roleDefinitionId = roleDefinitionId;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public String getPrincipalType() {
		return principalType;
	}

	public void setPrincipalType(String principalType) {
		this.principalType = principalType;
	}

	private String roleDefinitionId;
			private String principalId;
			private String principalType;
		}