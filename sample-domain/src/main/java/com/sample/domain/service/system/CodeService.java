package com.sample.domain.service.system;

import static com.sample.common.util.ValidateUtils.isEquals;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.sample.domain.dto.common.Page;
import com.sample.domain.dto.common.Pageable;
import com.sample.domain.dto.system.Code;
import com.sample.domain.dto.system.CodeCriteria;
import com.sample.domain.exception.NoDataFoundException;
import com.sample.domain.repository.system.CodeRepository;
import com.sample.domain.service.BaseTransactionalService;

/**
 * コードサービス
 */
@Service
public class CodeService extends BaseTransactionalService {

    @Autowired
    CodeRepository codeRepository;

    /**
     * コードを複数取得します。
     *
     * @return
     */
    @Transactional(readOnly = true) // 読み取りのみの場合は指定する
    public Page<Code> findAll(CodeCriteria criteria, Pageable pageable) {
        Assert.notNull(criteria, "criteria must not be null");
        return codeRepository.findAll(criteria, pageable);
    }

    /**
     * コードを取得します。
     *
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<Code> findOne(CodeCriteria criteria) {
        Assert.notNull(criteria, "criteria must not be null");
        return codeRepository.findOne(criteria);
    }

    /**
     * コードを取得します。
     *
     * @return
     */
    @Transactional(readOnly = true)
    public Code findById(final Long id) {
        Assert.notNull(id, "id must not be null");
        return codeRepository.fetchAll().stream().filter(c -> c.getId() == id.longValue()).findFirst()
                .orElseThrow(() -> new NoDataFoundException("id=" + id + " のデータが見つかりません。"));
    }

    /**
     * コードを取得します。
     * 
     * @param codeKey
     * @return
     */
    @Transactional(readOnly = true)
    public Code findByKey(final String codeKey) {
        Assert.notNull(codeKey, "codeKey must not be null");
        return codeRepository.fetchAll().stream().filter(c -> isEquals(codeKey, c.getCodeKey())).findFirst()
                .orElseThrow(() -> new NoDataFoundException("code_key=" + codeKey + " のデータが見つかりません。"));
    }

    /**
     * コードを追加します。
     *
     * @param inputCode
     * @return
     */
    public Code create(final Code inputCode) {
        Assert.notNull(inputCode, "inputCode must not be null");
        return codeRepository.create(inputCode);
    }

    /**
     * コードを更新します。
     *
     * @param inputCode
     * @return
     */
    public Code update(final Code inputCode) {
        Assert.notNull(inputCode, "inputCode must not be null");
        return codeRepository.update(inputCode);
    }

    /**
     * コードを論理削除します。
     *
     * @return
     */
    public Code delete(final Long id) {
        Assert.notNull(id, "id must not be null");
        return codeRepository.delete(id);
    }
}
