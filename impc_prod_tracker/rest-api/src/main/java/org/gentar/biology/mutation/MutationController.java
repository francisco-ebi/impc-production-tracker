package org.gentar.biology.mutation;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class MutationController
{
    private MutationMapper mutationMapper;
    private MutationResponseMapper mutationResponseMapper;
    private MutationService mutationService;

    public MutationController(
        MutationMapper mutationMapper,
        MutationResponseMapper mutationResponseMapper,
        MutationService mutationService)
    {
        this.mutationMapper = mutationMapper;
        this.mutationResponseMapper = mutationResponseMapper;
        this.mutationService = mutationService;
    }

    /**
     * Gets a mutation in an outcome.
     * @param pin Public identifier of the plan.
     * @param tpo Public identifier of the outcome
     * @param id Id of thr mutation.
     * @return Mutation DTO
     */
    @GetMapping(value = {"plans/{pin}/outcomes/{tpo}/mutations/{id}"})
    public MutationResponseDTO findMutationInOutcomeById(
        @PathVariable String pin, @PathVariable String tpo, @PathVariable Long id)
    {
        Mutation mutation = mutationService.getMutationByPinTpoAndId(pin, tpo, id);
        return mutationResponseMapper.toDto(mutation);
    }
}
