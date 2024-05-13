package lk.ac.uwu.cvi.service.impl;

import lk.ac.uwu.cvi.dto.request.DiagnosisCharacteristicResultRequestDTO;
import lk.ac.uwu.cvi.dto.response.DiagnoseResultResponseDTO;
import lk.ac.uwu.cvi.entity.DiagnosisCharacteristic;
import lk.ac.uwu.cvi.enums.Characteristic;
import lk.ac.uwu.cvi.repository.DiagnosisCharacteristicRepository;
import lk.ac.uwu.cvi.service.ScoreCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScoreCalculationServiceImpl implements ScoreCalculationService {

    private final DiagnosisCharacteristicRepository diagnosisCharacteristicRepository;

    @Override
    public DiagnoseResultResponseDTO calculateCharacteristicResult(DiagnosisCharacteristicResultRequestDTO request) {
        System.out.println("calculation service - " + request);
        Long diagnosisCharacteristicId = request.getDiagnosisCharacteristicId();
        Optional<DiagnosisCharacteristic> optionalDiagnosisCharacteristic = diagnosisCharacteristicRepository.findById(diagnosisCharacteristicId);
        double score = 0;

        if (optionalDiagnosisCharacteristic.isPresent()) {
            DiagnosisCharacteristic diagnosisCharacteristic = optionalDiagnosisCharacteristic.get();
            Characteristic characteristic = diagnosisCharacteristic.getCharacteristic();

            switch (characteristic) {
                case COLOR_PREFERENCE -> score = colorPreference(request.getResult(), diagnosisCharacteristic);
                case ATTENTION_TO_LIGHT -> score = attentionToLight(request.getResult(), diagnosisCharacteristic);
                case ATTENTION_TO_MOVEMENT -> score = attentionToMovement(request.getResult(), diagnosisCharacteristic);
                case VISUAL_COMPLEXITY -> score = visualComplexity(request.getResult(), diagnosisCharacteristic);
                case DIFFICULTY_IN_VISUAL_NOVELTY ->
                        score = visualNovelty(request.getResult(), diagnosisCharacteristic);
            }
        }
        return new DiagnoseResultResponseDTO(score, null);
    }

    @Override
    public DiagnoseResultResponseDTO calculateDiagnosisResult(Long diagnoseId) {
        return new DiagnoseResultResponseDTO(null, null);
    }

    private double colorPreference(List<DiagnosisCharacteristicResultRequestDTO.Result> results, DiagnosisCharacteristic diagnosisCharacteristic) {

        // Convert milliseconds to seconds and group by second
        Map<String, List<DiagnosisCharacteristicResultRequestDTO.Result>> groupedBySecond = results.stream()
                .collect(Collectors.groupingBy(result -> result.getTime().split(" ")[3]));

        // Calculate the looking direction with maximum count for each second
        TreeMap<String, String> maxLookingDirections = new TreeMap<>();
        for (Map.Entry<String, List<DiagnosisCharacteristicResultRequestDTO.Result>> entry : groupedBySecond.entrySet()) {
            Map<String, Long> countByLookingDirection = entry.getValue().stream()
                    .collect(Collectors.groupingBy(DiagnosisCharacteristicResultRequestDTO.Result::getLooking, Collectors.counting()));
            String maxLookingDirection = Collections.max(countByLookingDirection.entrySet(), Map.Entry.comparingByValue())
                    .getKey();
            maxLookingDirections.put(entry.getKey(), maxLookingDirection);
        }

//        List<String> lookings = maxLookingDirections.values().stream().toList();
        List<String> lookings = results.stream().map(DiagnosisCharacteristicResultRequestDTO.Result::getLooking).toList();

        double rightCount = 0;
        double leftCount = 0;

        for (String l : lookings) {
            if (l.equals("right")) rightCount++;
            else leftCount++;
        }

        double probability = leftCount / (rightCount + leftCount);

        double score = 0;

        if (probability > 0.5) {
            score = 1;
        } else if (probability > 0.375) {
            score = 0.75;
        } else if (probability > 0.25) {
            score = 0.5;
        } else if (probability > 0.125) {
            score = 0.25;
        } else {
            score = 0;
        }

        System.out.println("r - " + rightCount);
        System.out.println("l - " + leftCount);
        System.out.println("p - " + probability);
        System.out.println("s - " + score);
        return score;
    }

    private double attentionToLight(List<DiagnosisCharacteristicResultRequestDTO.Result> results, DiagnosisCharacteristic diagnosisCharacteristic) {

        // Convert milliseconds to seconds and group by second
        Map<String, List<DiagnosisCharacteristicResultRequestDTO.Result>> groupedBySecond = results.stream()
                .collect(Collectors.groupingBy(result -> result.getTime().split(" ")[3]));

        // Calculate the looking direction with maximum count for each second
        TreeMap<String, String> maxLookingDirections = new TreeMap<>();
        for (Map.Entry<String, List<DiagnosisCharacteristicResultRequestDTO.Result>> entry : groupedBySecond.entrySet()) {
            Map<String, Long> countByLookingDirection = entry.getValue().stream()
                    .collect(Collectors.groupingBy(DiagnosisCharacteristicResultRequestDTO.Result::getLooking, Collectors.counting()));
            String maxLookingDirection = Collections.max(countByLookingDirection.entrySet(), Map.Entry.comparingByValue())
                    .getKey();
            maxLookingDirections.put(entry.getKey(), maxLookingDirection);
        }

//        List<String> lookings = maxLookingDirections.values().stream().toList();
        List<String> lookings = results.stream().map(DiagnosisCharacteristicResultRequestDTO.Result::getLooking).toList();

        double rightCount = 0;
        double leftCount = 0;

        for (String l : lookings) {
            if (l.equals("right")) rightCount++;
            else leftCount++;
        }

        double probability = leftCount / (rightCount + leftCount);

        double score = 0;

        if (probability > 0.5) {
            score = 1;
        } else if (probability > 0.375) {
            score = 0.75;
        } else if (probability > 0.25) {
            score = 0.5;
        } else if (probability > 0.125) {
            score = 0.25;
        } else {
            score = 0;
        }

        System.out.println("r - " + rightCount);
        System.out.println("l - " + leftCount);
        System.out.println("p - " + probability);
        System.out.println("s - " + score);
        return score;
    }

    private double attentionToMovement(List<DiagnosisCharacteristicResultRequestDTO.Result> results, DiagnosisCharacteristic diagnosisCharacteristic) {

        // Convert milliseconds to seconds and group by second
        Map<String, List<DiagnosisCharacteristicResultRequestDTO.Result>> groupedBySecond = results.stream()
                .collect(Collectors.groupingBy(result -> result.getTime().split(" ")[3]));

        // Calculate the looking direction with maximum count for each second
        TreeMap<String, String> maxLookingDirections = new TreeMap<>();
        for (Map.Entry<String, List<DiagnosisCharacteristicResultRequestDTO.Result>> entry : groupedBySecond.entrySet()) {
            Map<String, Long> countByLookingDirection = entry.getValue().stream()
                    .collect(Collectors.groupingBy(DiagnosisCharacteristicResultRequestDTO.Result::getLooking, Collectors.counting()));
            String maxLookingDirection = Collections.max(countByLookingDirection.entrySet(), Map.Entry.comparingByValue())
                    .getKey();
            maxLookingDirections.put(entry.getKey(), maxLookingDirection);
        }

//        List<String> lookings = maxLookingDirections.values().stream().toList();
        List<String> lookings = results.stream().map(DiagnosisCharacteristicResultRequestDTO.Result::getLooking).toList();

        double rightCount = 0;
        double leftCount = 0;

        for (String l : lookings) {
            if (l.equals("right")) rightCount++;
            else leftCount++;
        }

        double probability = leftCount / (rightCount + leftCount);

        double score = 0;

        if (probability > 0.5) {
            score = 1;
        } else if (probability > 0.375) {
            score = 0.75;
        } else if (probability > 0.25) {
            score = 0.5;
        } else if (probability > 0.125) {
            score = 0.25;
        } else {
            score = 0;
        }

        System.out.println("r - " + rightCount);
        System.out.println("l - " + leftCount);
        System.out.println("p - " + probability);
        System.out.println("s - " + score);
        return score;
    }

    private double visualComplexity(List<DiagnosisCharacteristicResultRequestDTO.Result> results, DiagnosisCharacteristic diagnosisCharacteristic) {

        // Convert milliseconds to seconds and group by second
        Map<String, List<DiagnosisCharacteristicResultRequestDTO.Result>> groupedBySecond = results.stream()
                .collect(Collectors.groupingBy(result -> result.getTime().split(" ")[3]));

        // Calculate the looking direction with maximum count for each second
        TreeMap<String, String> maxLookingDirections = new TreeMap<>();
        for (Map.Entry<String, List<DiagnosisCharacteristicResultRequestDTO.Result>> entry : groupedBySecond.entrySet()) {
            Map<String, Long> countByLookingDirection = entry.getValue().stream()
                    .collect(Collectors.groupingBy(DiagnosisCharacteristicResultRequestDTO.Result::getLooking, Collectors.counting()));
            String maxLookingDirection = Collections.max(countByLookingDirection.entrySet(), Map.Entry.comparingByValue())
                    .getKey();
            maxLookingDirections.put(entry.getKey(), maxLookingDirection);
        }

//        List<String> lookings = maxLookingDirections.values().stream().toList();
        List<String> lookings = results.stream().map(DiagnosisCharacteristicResultRequestDTO.Result::getLooking).toList();

        double rightCount = 0;
        double leftCount = 0;

        for (String l : lookings) {
            if (l.equals("right")) rightCount++;
            else leftCount++;
        }

        double probability = leftCount / (rightCount + leftCount);

        double score = 0;

        if (probability > 0.5) {
            score = 1;
        } else if (probability > 0.375) {
            score = 0.75;
        } else if (probability > 0.25) {
            score = 0.5;
        } else if (probability > 0.125) {
            score = 0.25;
        } else {
            score = 0;
        }

        System.out.println("r - " + rightCount);
        System.out.println("l - " + leftCount);
        System.out.println("p - " + probability);
        System.out.println("s - " + score);
        return score;
    }

    private double visualNovelty(List<DiagnosisCharacteristicResultRequestDTO.Result> results, DiagnosisCharacteristic diagnosisCharacteristic) {

        // Convert milliseconds to seconds and group by second
        Map<String, List<DiagnosisCharacteristicResultRequestDTO.Result>> groupedBySecond = results.stream()
                .collect(Collectors.groupingBy(result -> result.getTime().split(" ")[3]));

        // Calculate the looking direction with maximum count for each second
        TreeMap<String, String> maxLookingDirections = new TreeMap<>();
        for (Map.Entry<String, List<DiagnosisCharacteristicResultRequestDTO.Result>> entry : groupedBySecond.entrySet()) {
            Map<String, Long> countByLookingDirection = entry.getValue().stream()
                    .collect(Collectors.groupingBy(DiagnosisCharacteristicResultRequestDTO.Result::getLooking, Collectors.counting()));
            String maxLookingDirection = Collections.max(countByLookingDirection.entrySet(), Map.Entry.comparingByValue())
                    .getKey();
            maxLookingDirections.put(entry.getKey(), maxLookingDirection);
        }

//        List<String> lookings = maxLookingDirections.values().stream().toList();
        List<String> lookings = results.stream().map(DiagnosisCharacteristicResultRequestDTO.Result::getLooking).toList();

        double upCount = 0;
        double bottomCount = 0;
        double rightCount = 0;
        double leftCount = 0;
        double upperRightCount = 0;
        double upperLeftCount = 0;
        double bottomRightCount = 0;
        double bottomLeftCount = 0;

        for (String l : lookings) {
            if (l.equals("right")) rightCount++;
            else if (l.equals("left")) leftCount++;
            else if (l.equals("up")) upCount++;
            else if (l.equals("bottom")) bottomCount++;
            else if (l.equals("upper right")) upperRightCount++;
            else if (l.equals("upper left")) upperLeftCount++;
            else if (l.equals("bottom left")) bottomLeftCount++;
            else if (l.equals("bottom right")) bottomRightCount++;
        }

        double probability = (upperRightCount + bottomLeftCount) / (upCount + bottomCount + rightCount + leftCount + upperRightCount + upperLeftCount + bottomRightCount + bottomLeftCount);

        double score = 0;

        if (probability > 0.5) {
            score = 1;
        } else if (probability > 0.375) {
            score = 0.75;
        } else if (probability > 0.25) {
            score = 0.5;
        } else if (probability > 0.125) {
            score = 0.25;
        } else {
            score = 0;
        }

        System.out.println("r - " + rightCount);
        System.out.println("l - " + leftCount);
        System.out.println("u - " + upCount);
        System.out.println("b - " + bottomCount);
        System.out.println("ur - " + upperRightCount);
        System.out.println("ul - " + upperLeftCount);
        System.out.println("br - " + bottomRightCount);
        System.out.println("bl - " + bottomLeftCount);
        System.out.println("p - " + probability);
        System.out.println("s - " + score);
        return score;
    }
}
