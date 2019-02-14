package com.sample.domain.dao.system

import com.sample.domain.dao.system.RoleDao
import com.sample.domain.dto.common.Pageable
import com.sample.domain.dto.system.Role
import com.sample.domain.dto.system.Role
import com.sample.domain.dto.system.RoleCriteria
import com.sample.domain.exception.NoDataFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static com.sample.domain.util.DomaUtils.createSelectOptions
import static java.util.stream.Collectors.toList
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@SpringBootTest(webEnvironment = NONE)
@Transactional // テスト後にロールバックする
class RoleDaoTest extends Specification {

    @Autowired
    RoleDao roleDao

    def "存在しないIDで絞り込んだ場合、空のリストが返ること"() {
        when:
        def options = createSelectOptions(Pageable.DEFAULT).count()
        def criteria = new RoleCriteria()
        criteria.setId(-9999)

        def data = roleDao.selectAll(criteria, options, toList())

        then:
        data.size() == 0
    }

    def "存在しない役割キーで絞り込んだ場合、emptyが返ること"() {
        when:
        def criteria = new RoleCriteria()
        criteria.setRoleKey("XXXXXXXXXX")

        Optional<Role> role = roleDao.select(criteria)

        then:
        role == Optional.empty()
    }

    def "存在しないIDで絞り込んだ場合、emptyが返ること"() {
        when:
        Optional<Role> role = roleDao.selectById(-9999)

        then:
        role == Optional.empty()
    }

    def "改定番号を指定しないで更新した場合、例外がスローされること"() {
        when:
        def role = new Role()
        role.setRoleName("XXXXXXXXXX")
        roleDao.update(role)

        then:
        thrown(OptimisticLockingFailureException)
    }

    def "存在するデータを指定して更新した場合、更新件数が1件になること"() {
        when:
        def role = roleDao.selectById(1)
                .orElseThrow({ new NoDataFoundException("データが見つかりません。") })

        role.setRoleName("XXXXXXXXXX")
        def updated = roleDao.update(role)

        then:
        updated == 1
    }
}
