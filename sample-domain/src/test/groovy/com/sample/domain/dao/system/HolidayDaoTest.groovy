package com.sample.domain.dao.system

import com.sample.domain.dao.system.HolidayDao
import com.sample.domain.dto.common.Pageable
import com.sample.domain.dto.system.Holiday
import com.sample.domain.dto.system.HolidayCriteria
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
class HolidayDaoTest extends Specification {

    @Autowired
    HolidayDao holidayDao

    def "存在しないIDで絞り込んだ場合、空のリストが返ること"() {
        when:
        def options = createSelectOptions(Pageable.DEFAULT).count()
        def criteria = new HolidayCriteria()
        criteria.setId(-9999)

        def data = holidayDao.selectAll(criteria, options, toList())

        then:
        data.size() == 0
    }

    def "存在しない名称で絞り込んだ場合、emptyが返ること"() {
        when:
        def criteria = new HolidayCriteria()
        criteria.setHolidayName("XXXXXXXXXX")

        Optional<Holiday> holiday = holidayDao.select(criteria)

        then:
        holiday == Optional.empty()
    }

    def "存在しないIDで絞り込んだ場合、emptyが返ること"() {
        when:
        Optional<Holiday> holiday = holidayDao.selectById(-9999)

        then:
        holiday == Optional.empty()
    }

    def "改定番号を指定しないで更新した場合、例外がスローされること"() {
        when:
        def holiday = new Holiday()
        holiday.setHolidayName("XXXXXXXXXX")
        holidayDao.update(holiday)

        then:
        thrown(OptimisticLockingFailureException)
    }

    def "存在するデータを指定して更新した場合、更新件数が1件になること"() {
        when:
        def holiday = holidayDao.selectById(1)
                .orElseThrow({ new NoDataFoundException("データが見つかりません。") })

        holiday.setHolidayName("XXXXXXXXXX")
        def updated = holidayDao.update(holiday)

        then:
        updated == 1
    }
}
