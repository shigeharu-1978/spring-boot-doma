package com.sample.domain.dto.system;

import org.seasar.doma.*;

import com.sample.domain.dto.common.DomaDtoImpl;

import lombok.Getter;
import lombok.Setter;

@Table(name = "staff_roles")
@Entity
@Getter
@Setter
public class StaffRole extends DomaDtoImpl {

    private static final long serialVersionUID = 1780669742437422350L;

    // x担当者役割ID
    @Id
    @Column(name = "staff_role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // x担当者ID
    Long staffId;

    // x役割キー
    String roleKey;

    // x役割名
    String roleName;

    // x権限キー
    String permissionKey;

    // x権限名
    String permissionName;
}
