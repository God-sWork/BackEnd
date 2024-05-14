package gods.work.backend.service;

import gods.work.backend.domain.Trainer;
import gods.work.backend.dto.MailDto;
import gods.work.backend.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class EmailService {

    private final JavaMailSender mailSender;
    private final TrainerRepository trainerRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String FROM_ADDRESS = "godswork.noreply@gmail.com";

    public MailDto createMailAndChargePassword(Trainer trainer) {
        String newPassword = getTempPassword();
        MailDto mailDto = new MailDto();
        mailDto.setTo(trainer.getEmail());
        mailDto.setSubject(trainer.getTrainerLoginId() + "님의 임시 비밀번호 안내 이메일 입니다.");
        mailDto.setContent("임시 비밀번호는 [" + newPassword + "] 입니다. 새로운 비밀번호를 등록해주세요.");
        updatePassword(newPassword, trainer.getTrainerId());
        return mailDto;
    }

    public void updatePassword(String newPassword, int trainerId) {
        trainerRepository.updatePasswordByTrainerId(trainerId, passwordEncoder.encode(newPassword));
    }

    private String getTempPassword() {
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        String str = "";

        int idx = 0;
        for (int i=0; i<10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    public void mailSend(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getTo());
        message.setFrom(FROM_ADDRESS);
        message.setSubject(mailDto.getSubject());
        message.setText(mailDto.getContent());

        mailSender.send(message);
    }

}
