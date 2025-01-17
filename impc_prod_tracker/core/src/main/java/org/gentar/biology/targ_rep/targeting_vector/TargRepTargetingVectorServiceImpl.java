package org.gentar.biology.targ_rep.targeting_vector;

import org.gentar.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


/**
 * TargRepTargetingVectorServiceImpl.
 */
@Component
public class TargRepTargetingVectorServiceImpl implements TargRepTargetingVectorService {
    private final TargRepTargetingVectorRepository targetingVectorRepository;
    private static final String TARG_REP_TARGETING_VECTOR_NOT_EXISTS_ERROR =
        "A targ rep Targeting Vector with the id [%s] does not exist.";

    public TargRepTargetingVectorServiceImpl(
        TargRepTargetingVectorRepository targetingVectorRepository) {
        this.targetingVectorRepository = targetingVectorRepository;
    }


    @Override
    public TargRepTargetingVector getNotNullTargRepTargetingVectorById(Long id) {
        TargRepTargetingVector targRepTargetingVector =
            targetingVectorRepository.findTargRepTargetingVectorById(id);
        if (targRepTargetingVector == null) {
            throw new NotFoundException(
                String.format(TARG_REP_TARGETING_VECTOR_NOT_EXISTS_ERROR, id));
        }
        return targRepTargetingVector;
    }

    @Override
    public Page<TargRepTargetingVector> getPageableTargRepTargetingVector(Pageable page) {
        return targetingVectorRepository.findAll(page);
    }
}
