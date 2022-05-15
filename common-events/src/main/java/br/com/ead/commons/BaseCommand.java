package br.com.ead.commons;

import br.com.ead.Message;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class BaseCommand extends Message {
}